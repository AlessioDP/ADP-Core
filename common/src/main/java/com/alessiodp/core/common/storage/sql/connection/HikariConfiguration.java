package com.alessiodp.core.common.storage.sql.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public abstract class HikariConfiguration implements ConnectionFactory {
	@Setter @Getter private String tablePrefix = "";
	@Getter private HikariDataSource dataSource;
	@Getter private Jdbi jdbi;
	@Getter private boolean failed;
	
	@Setter protected String serverName;
	@Setter protected String port;
	@Setter protected String databaseName;
	
	@Setter protected String poolName;
	@Setter protected String username;
	@Setter protected String password;
	@Setter protected int maximumPoolSize = 10;
	@Setter protected int maxLifetime = 1800000;
	@Setter protected String charset = "utf8";
	@Setter protected boolean useSSL = false;
	
	@Override
	public void init() {
		failed = true;
		
		HikariConfig config = new HikariConfig();
		
		config.setPoolName(poolName);
		config.setUsername(username);
		config.setPassword(password);
		
		setupCredentials(config);
		setupProperties(config);
		
		config.setMaximumPoolSize(maximumPoolSize);
		config.setMinimumIdle(maximumPoolSize);
		config.setMaxLifetime(maxLifetime);
		config.setConnectionTimeout(5000);
		
		dataSource = load(config);
		
		jdbi = Jdbi.create(dataSource);
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.define("prefix", tablePrefix);
		
		failed = false;
	}
	
	@Override
	public void stop() {
		if (dataSource != null)
			dataSource.close();
	}
	
	@Override
	public void setDatabaseUrl(String databaseUrl) {
		throw new IllegalStateException("Not supported with Hikari connections");
	}
	
	public abstract void setupCredentials(HikariConfig config);
	
	public abstract void setupProperties(HikariConfig config);
	
	public HikariDataSource load(HikariConfig config) {
		return new HikariDataSource(config);
	}
}
