package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistoryMariaDBDao extends SchemaHistoryDao {
	String QUERY_CREATE = "CREATE TABLE IF NOT EXISTS `<prefix>schema_history` (" +
			"`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
			" `version` INT NOT NULL," +
			" `description` VARCHAR(25) NOT NULL," +
			" `script_name` VARCHAR(25) NOT NULL," +
			" `install_date` BIGINT NOT NULL)";
	
	@Override
	@SqlUpdate(QUERY_CREATE)
	void create();
}
