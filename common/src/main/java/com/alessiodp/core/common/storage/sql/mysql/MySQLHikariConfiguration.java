package com.alessiodp.core.common.storage.sql.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class MySQLHikariConfiguration {
	private final String poolName;
	private final String serverName;
	private final String port;
	private final String databaseName;
	private final String username;
	private final String password;
	@Setter private int maximumPoolSize = 10;
	@Setter private int maxLifetime = 1800000;
	@Setter private String characterEncoding = "utf8";
	@Setter private boolean useSSL = false;
	
	public HikariDataSource setup() {
		HikariConfig config = new HikariConfig();
		config.setPoolName(poolName);
		
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", serverName);
		config.addDataSourceProperty("port", port);
		config.addDataSourceProperty("databaseName", databaseName);
		
		config.setUsername(username);
		config.setPassword(password);
		config.setMaximumPoolSize(maximumPoolSize);
		config.setMinimumIdle(maximumPoolSize);
		config.setMaxLifetime(maxLifetime);
		config.setConnectionTimeout(5000);
		
		// Properties: https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
		config.addDataSourceProperty("cachePrepStmts", "true"); // Enable Prepared Statement caching
		config.addDataSourceProperty("prepStmtCacheSize", "25"); // How many PS cache, default: 25
		config.addDataSourceProperty("useServerPrepStmts", "true"); // If supported use PS server-side
		config.addDataSourceProperty("useLocalSessionState", "true"); // Enable setAutoCommit
		config.addDataSourceProperty("useLocalTransactionState", "true"); // Enable commit/rollbacks
		config.addDataSourceProperty("allowMultiQueries", "true"); // Support multiple queries, used to create tables
		config.addDataSourceProperty("useUnicode", "true"); // Forcing the use of unicode
		config.addDataSourceProperty("characterEncoding", characterEncoding); // Setup encoding to UTF-8
		config.addDataSourceProperty("useSSL", Boolean.toString(useSSL));
		
		return new HikariDataSource(config);
	}
}
