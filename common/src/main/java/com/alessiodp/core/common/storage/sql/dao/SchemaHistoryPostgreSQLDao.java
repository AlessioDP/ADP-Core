package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistoryPostgreSQLDao extends SchemaHistoryDao {
	String QUERY_CREATE = "CREATE TABLE IF NOT EXISTS <prefix>schema_history (" +
			"\"id\" SERIAL PRIMARY KEY NOT NULL," +
			" \"version\" INT NOT NULL," +
			" \"description\" VARCHAR(25) NOT NULL," +
			" \"script_name\" VARCHAR(25) NOT NULL," +
			" \"install_date\" BIGINT NOT NULL)";
	String QUERY_DROP = "DROP TABLE IF EXISTS <prefix>schema_history";
	String QUERY_INSERT = "INSERT INTO <prefix>schema_history (\"version\", \"description\", \"script_name\", \"install_date\") VALUES (?, ?, ?, ?)";
	String QUERY_HIGHER_VERSION = "SELECT max(\"version\") FROM <prefix>schema_history";
	String QUERY_COUNT_VERSIONS = "SELECT count(*) FROM <prefix>schema_history";
	
	@Override
	@SqlUpdate(QUERY_CREATE)
	void create();
	
	@Override
	@SqlUpdate(QUERY_DROP)
	void drop();
	
	@Override
	@SqlUpdate(QUERY_INSERT)
	void insert(int version, String description, String script, long install);
	
	@Override
	@SqlQuery(QUERY_HIGHER_VERSION)
	int higherVersion();
	
	@Override
	@SqlQuery(QUERY_COUNT_VERSIONS)
	int countVersions();
}
