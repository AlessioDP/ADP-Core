package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.sqlite.SQLiteDataSource;

import java.nio.file.Path;

public class SQLiteConnectionFactory implements ConnectionFactory {
	@Setter private String databaseUrl;
	@Setter private String tablePrefix = "";
	@Getter private SQLiteDataSource dataSource;
	@Getter private Jdbi jdbi;
	@Getter private boolean failed;
	
	public SQLiteConnectionFactory(@NonNull String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	
	public SQLiteConnectionFactory(@NonNull Path databasePath) {
		this.databaseUrl = "jdbc:sqlite:" + databasePath.toString();
	}
	
	@Override
	public void init() {
		failed = true;
		dataSource = new SQLiteDataSource();
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
		return StorageType.SQLITE;
	}
}
