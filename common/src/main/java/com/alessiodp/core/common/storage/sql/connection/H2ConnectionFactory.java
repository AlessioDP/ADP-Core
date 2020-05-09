package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.nio.file.Path;

public class H2ConnectionFactory implements ConnectionFactory {
	@Setter private String databaseUrl;
	@Setter private String tablePrefix = "";
	@Getter private JdbcDataSource dataSource;
	@Getter private Jdbi jdbi;
	@Getter private boolean failed;
	
	public H2ConnectionFactory(@NonNull String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	
	public H2ConnectionFactory(@NonNull Path databasePath) {
		this.databaseUrl = "jdbc:h2:" + databasePath.toString();
	}
	
	@Override
	public void init() {
		failed = true;
		dataSource = new JdbcDataSource();
		dataSource.setUrl(databaseUrl);
		
		jdbi = Jdbi.create(dataSource);
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.define("prefix", tablePrefix);
		
		failed = false;
	}
	
	@Override
	public void stop() {
		// Nothing to do
	}
	
	@Override
	public StorageType getType() {
		return StorageType.H2;
	}
}
