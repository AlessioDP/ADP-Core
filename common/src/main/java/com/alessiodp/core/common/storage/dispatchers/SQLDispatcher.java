package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import com.alessiodp.core.common.storage.interfaces.ISQLUpgradeManager;
import com.alessiodp.core.common.storage.sql.ISQLTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public abstract class SQLDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	
	protected IDatabaseSQL database;
	protected StorageType databaseType;
	
	protected ISQLUpgradeManager upgradeManager;
	
	@Override
	public final void stop() {
		if (!isFailed())
			database.stopSQL();
	}
	
	@Override
	public final boolean isFailed() {
		return database == null || database.isFailed();
	}
	
	public final Connection getConnection() {
		return database.getConnection();
	}
	
	protected void createTable(Connection connection, ISQLTable table) {
		try (Statement statement = connection.createStatement()) {
			// MySQL
			String versionQuery = Constants.QUERY_CHECKVERSION_SET_MYSQL;
			if (databaseType == StorageType.SQLITE) {
				// SQLite
				versionQuery = Constants.QUERY_CHECKVERSION_SET_SQLITE;
			}
			
			int versionValue = table.getVersion();
			
			// Create table
			statement.executeUpdate(table.formatQuery(table.getCreateQuery()));
			
			// Change version into the versions table
			if (!table.getTypeName().equalsIgnoreCase("version")) {
				try (PreparedStatement preStatement = connection.prepareStatement(table.formatQuery(versionQuery))) {
					preStatement.setString(1, table.getTableName());
					preStatement.setInt(2, versionValue);
					preStatement.executeUpdate();
				}
			}
		} catch (SQLException ex) {
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_SQL_ERROR_TABLE
					.replace("{table}", table.getTypeName()), ex);
		}
	}
	
	protected void initTables(Connection connection, LinkedList<ISQLTable> tables) {
		try {
			DatabaseMetaData metadata = connection.getMetaData();
			for (ISQLTable table : tables) {
				try (ResultSet rs = metadata.getTables(null, null, table.getTableName(), null)) {
					if (rs.next()) {
						upgradeManager.checkUpgrades(this, connection, table, databaseType); // Checking for porting
					} else {
						createTable(connection, table); // Create table
					}
				} catch (SQLException ex) {
					plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_SQL_ERROR, ex);
				}
			}
		} catch (Exception ex) {
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_SQL_ERROR, ex);
		}
	}
	
	protected String renameTable(Connection connection, String table, String tableSuffix) throws SQLException {
		String ret;
		// Load existing tables
		List<String> listTables = new ArrayList<>();
		DatabaseMetaData metadata = connection.getMetaData();
		try (ResultSet rs = metadata.getTables(null, null, "%", null)) {
			while (rs.next()) {
				listTables.add(rs.getString(3));
			}
		}
		
		String newTable = table + tableSuffix;
		int count = 1;
		while (listTables.contains(newTable)) {
			newTable = table + tableSuffix + count;
			count++;
		}
		
		try (Statement statement = connection.createStatement()) {
			String query = Constants.QUERY_RENAME_MYSQL;
			if (databaseType == StorageType.SQLITE)
				query = Constants.QUERY_RENAME_SQLITE;
			
			statement.executeUpdate(query
					.replace("{table}", table)
					.replace("{newtable}", newTable));
			ret = newTable;
		}
		return ret;
	}
}