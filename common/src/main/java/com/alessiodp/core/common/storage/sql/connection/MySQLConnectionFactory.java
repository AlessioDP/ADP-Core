package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import com.zaxxer.hikari.HikariConfig;

public class MySQLConnectionFactory extends HikariConfiguration {
	
	@Override
	public void setupCredentials(HikariConfig config) {
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName);
		
		config.setUsername(username);
		config.setPassword(password);
	}
	
	@Override
	public void setupProperties(HikariConfig config) {
		config.addDataSourceProperty("cachePrepStmts", "true"); // Enable Prepared Statement caching
		config.addDataSourceProperty("prepStmtCacheSize", "25"); // How many PS cache, default: 25
		config.addDataSourceProperty("useServerPrepStmts", "true"); // If supported use PS server-side
		config.addDataSourceProperty("useLocalSessionState", "true"); // Enable setAutoCommit
		config.addDataSourceProperty("useLocalTransactionState", "true"); // Enable commit/rollbacks
		config.addDataSourceProperty("allowMultiQueries", "true"); // Support multiple queries, used to create tables
		config.addDataSourceProperty("useUnicode", "true"); // Forcing the use of unicode
		config.addDataSourceProperty("serverTimezone", "UTC"); // Necessary to run tests
		config.addDataSourceProperty("characterEncoding", charset); // Setup encoding to UTF-8
		config.addDataSourceProperty("allowPublicKeyRetrieval", "true");
		config.addDataSourceProperty("useSSL", Boolean.toString(useSSL));
	}
	
	@Override
	public StorageType getType() {
		return StorageType.MYSQL;
	}
}
