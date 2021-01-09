package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import com.zaxxer.hikari.HikariConfig;

public class MariaDBConnectionFactory extends HikariConfiguration {
	
	@Override
	public void setupCredentials(HikariConfig config) {
		config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
		config.addDataSourceProperty("serverName", serverName);
		config.addDataSourceProperty("port", port);
		config.addDataSourceProperty("databaseName", databaseName);
		
		config.setUsername(username);
		config.setPassword(password);
	}
	
	@Override
	public void setupProperties(HikariConfig config) {
		// Nothing to do
	}
	
	@Override
	public StorageType getType() {
		return StorageType.MARIADB;
	}
}
