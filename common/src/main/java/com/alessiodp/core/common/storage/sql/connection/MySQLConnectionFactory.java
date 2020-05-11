package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class MySQLConnectionFactory extends HikariConfiguration implements ConnectionFactory {
	@Setter private String tablePrefix = "";
	@Getter private HikariDataSource dataSource;
	@Getter private Jdbi jdbi;
	@Getter private boolean failed;
	
	@Override
	public void init() {
		failed = true;
		setDataSource("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		setup();
		setupMySQLProperties();
		
		dataSource = load();
		
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
	public StorageType getType() {
		return StorageType.MYSQL;
	}
}
