package com.alessiodp.core.common.storage.sql.mysql;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.dispatchers.SQLDispatcher;
import com.alessiodp.core.common.storage.sql.ISQLTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the utility class that handles SQL database upgrades.
 *
 * Basically, you have a table that contains other tables versions.
 * The plugin will check if that version number is up to date, if not, it will re-create the table
 *   and will copy the data from the old one (depending on the new table) into the new one.
 *
 * An example below:
 * You have A, B and C into the table version 1.
 * If you have a new table, version 2, that adds a D, you have to get from the old table A, B, C
 *   and add D, with default value, into the create query.
 */
@RequiredArgsConstructor
public abstract class SQLUpgradeManager {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final SQLDispatcher dispatcher;
	@NonNull protected final StorageType storageType;
	
	// Upgrade settings
	private final boolean saveOld;
	private final String saveSuffix;
	
	/**
	 * Check for table upgrades
	 *
	 * @param connection the connection to use
	 * @param table the table to upgrade
	 */
	public void checkForUpgrades(Connection connection, ISQLTable table) {
		if (isVersionTable(table)) {
			// Versions table must not be updated!
			// Avoiding problems with table changes
			return;
		}
		
		int currentVersion = -1;
		try (PreparedStatement statement = connection.prepareStatement(formatQuery(Constants.QUERY_CHECKVERSION))) {
			statement.setString(1, table.getTableName());
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					currentVersion = rs.getInt("version");
				}
			}
		} catch (SQLException ex) {
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_SQL_ERROR, ex);
		}
		
		if (currentVersion < table.getVersion()) {
			// Upgrade table
			plugin.getLoggerManager().log(Constants.DEBUG_SQL_UPGRADING
					.replace("{table}", table.getTableName()), true);
			upgradeGenericTable(connection, table, currentVersion);
		}
	}
	
	/**
	 * Update a generic table
	 *
	 * @param connection the connection to use
	 * @param table the table to use
	 * @param currentVersion the current version of the table
	 */
	private void upgradeGenericTable(Connection connection, ISQLTable table, int currentVersion) {
		try (Statement statement = connection.createStatement()) {
			connection.setAutoCommit(false);
			
			String tempSuffix = saveOld ? saveSuffix : "_temp";
			
			String renamedTableName = dispatcher.renameTable(connection, table.getTableName(), tempSuffix);
			dispatcher.createTable(connection, table);
			
			try (ResultSet rs = statement.executeQuery(Constants.QUERY_GENERIC_SELECTALL
					.replace("{table}", renamedTableName))) {
				upgradeTable(connection, rs, table, currentVersion);
			}
			
			if (!saveOld) {
				statement.executeUpdate(Constants.QUERY_GENERIC_DROP
						.replace("{table}", renamedTableName));
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException ignored) {}
			
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_SQL_ERROR_TABLE
					.replace("{table}", table.getTableName()), ex);
		}
	}
	
	/**
	 * Upgrade the table
	 *
	 * @param connection the connection to use
	 * @param rs the ResultSet of the query
	 * @param table the table to upgrade
	 * @param currentVersion the current version of the table
	 * @throws SQLException if something goes wrong
	 */
	protected abstract void upgradeTable(Connection connection, ResultSet rs, ISQLTable table, int currentVersion) throws SQLException;
	
	/**
	 * Check if the table is the version one
	 *
	 * @param table the table to check
	 * @return true if the given table is the version one
	 */
	protected abstract boolean isVersionTable(ISQLTable table);
	
	/**
	 * Format a query
	 *
	 * @param query the query to format
	 * @return the query formatted
	 */
	protected abstract String formatQuery(String query);
}
