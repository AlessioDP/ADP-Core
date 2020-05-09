package com.alessiodp.core.common.storage.sql.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;

public abstract class HikariConfiguration {
	protected HikariConfig config;
	
	@Setter private String jdbcUrl;
	@Setter private String serverName;
	@Setter private String port;
	@Setter private String databaseName;
	
	@Setter private String dataSource;
	@Setter private String poolName;
	@Setter private String username;
	@Setter private String password;
	@Setter private int maximumPoolSize = 10;
	@Setter private int maxLifetime = 1800000;
	@Setter private String charset = "utf8";
	@Setter private boolean useSSL = false;
	
	public void setup() {
		config = new HikariConfig();
		config.setPoolName(poolName);
		config.setUsername(username);
		config.setPassword(password);
		
		config.setDataSourceClassName(dataSource);
		if (jdbcUrl != null)
			config.setJdbcUrl(jdbcUrl);
		else {
			config.addDataSourceProperty("serverName", serverName);
			config.addDataSourceProperty("port", port);
			config.addDataSourceProperty("databaseName", databaseName);
		}
		
		config.setMaximumPoolSize(maximumPoolSize);
		config.setMinimumIdle(maximumPoolSize);
		config.setMaxLifetime(maxLifetime);
		config.setConnectionTimeout(5000);
	}
	
	public void setupMySQLProperties() {
		// Properties: https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
		config.addDataSourceProperty("cachePrepStmts", "true"); // Enable Prepared Statement caching
		config.addDataSourceProperty("prepStmtCacheSize", "25"); // How many PS cache, default: 25
		config.addDataSourceProperty("useServerPrepStmts", "true"); // If supported use PS server-side
		config.addDataSourceProperty("useLocalSessionState", "true"); // Enable setAutoCommit
		config.addDataSourceProperty("useLocalTransactionState", "true"); // Enable commit/rollbacks
		config.addDataSourceProperty("allowMultiQueries", "true"); // Support multiple queries, used to create tables
		config.addDataSourceProperty("useUnicode", "true"); // Forcing the use of unicode
		config.addDataSourceProperty("characterEncoding", charset); // Setup encoding to UTF-8
		config.addDataSourceProperty("useSSL", Boolean.toString(useSSL));
	}
	
	public HikariDataSource load() {
		return new HikariDataSource(config);
	}
	
	public void setDatabaseUrl(String databaseUrl) {
		config.setJdbcUrl(databaseUrl);
	}
}
