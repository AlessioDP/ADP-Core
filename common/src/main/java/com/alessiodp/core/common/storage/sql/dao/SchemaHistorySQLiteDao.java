package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistorySQLiteDao extends SchemaHistoryDao {
	@Override
	@SqlUpdate(
			"CREATE TABLE IF NOT EXISTS `<prefix>schema_history` (" +
					"`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
					" `version` INTEGER NOT NULL," +
					" `description` VARCHAR NOT NULL," +
					" `script_name` VARCHAR NOT NULL," +
					" `install_date` BIGINT NOT NULL)"
	)
	void create();
}
