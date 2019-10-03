package com.alessiodp.core.common.storage.sql.mysql;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.libraries.ILibrary;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
public class MySQLDao implements IDatabaseSQL {
	@NonNull private final ADPPlugin plugin;
	@NonNull private final MySQLHikariConfiguration hikariConfiguration;
	
	private HikariDataSource hikariDataSource;
	@Getter private boolean failed;
	
	/*
	 * Connection
	 */
	@Override
	public void initSQL() {
		failed = false;
		if (plugin.getLibraryManager().initLibrary(ILibrary.HIKARI)
				&& plugin.getLibraryManager().initLibrary(ILibrary.SLF4J_API)
				&& plugin.getLibraryManager().initLibrary(ILibrary.SLF4J_SIMPLE)) {
			try {
				hikariDataSource = hikariConfiguration.setup();
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_MYSQL
						.replace("{message}", ex.getMessage()));
			}
			
			// Test connection
			if (hikariDataSource == null || !ping()) {
				failed = true;
			}
		} else
			failed = true;
	}
	
	public boolean ping() {
		boolean ret = false;
		try (Connection conn = getConnection()) {
			try (Statement statement = conn.createStatement()) {
				statement.execute("/* ping */ SELECT 1;");
				ret = true;
			}
		} catch (SQLException ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_SQL_CONNECTIONERROR
					.replace("{storage}", StorageType.MYSQL.getFormattedName())
					.replace("{message}", ex.getMessage()));
		}
		return ret;
	}
	
	@Override
	public Connection getConnection() {
		Connection ret = null;
		try {
			ret = hikariDataSource.getConnection();
			
			if (ret == null)
				throw new SQLException("Failed to get connection from Hikari CP");
		} catch (SQLException ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_SQL_CONNECTIONERROR
					.replace("{storage}", StorageType.MYSQL.getFormattedName())
					.replace("{message}", ex.getMessage()));
		}
		return ret;
	}
	
	@Override
	public void stopSQL() {
		if (hikariDataSource != null && !hikariDataSource.isClosed()) {
			hikariDataSource.close();
		}
	}
}
