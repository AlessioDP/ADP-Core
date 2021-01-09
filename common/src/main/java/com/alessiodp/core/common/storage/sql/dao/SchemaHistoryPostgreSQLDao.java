package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistoryPostgreSQLDao extends SchemaHistoryDao {
	@Override
	@SqlUpdate(
			"CREATE TABLE IF NOT EXISTS <prefix>schema_history (" +
					"\"id\" SERIAL PRIMARY KEY NOT NULL," +
					" \"version\" INT NOT NULL," +
					" \"description\" VARCHAR(25) NOT NULL," +
					" \"script_name\" VARCHAR(25) NOT NULL," +
					" \"install_date\" BIGINT NOT NULL)"
	)
	void create();
	
	@Override
	@SqlUpdate("DROP TABLE IF EXISTS <prefix>schema_history")
	void drop();
	
	@Override
	@SqlUpdate("INSERT INTO <prefix>schema_history (\"version\", \"description\", \"script_name\", \"install_date\") VALUES (?, ?, ?, ?)")
	void insert(int version, String description, String script, long install);
	
	@Override
	@SqlQuery("SELECT max(\"version\") FROM <prefix>schema_history")
	int higherVersion();
	
	@Override
	@SqlQuery("SELECT count(*) FROM <prefix>schema_history")
	int countVersions();
}
