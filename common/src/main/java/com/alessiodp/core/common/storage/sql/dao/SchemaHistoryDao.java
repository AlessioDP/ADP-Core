package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistoryDao {
	/**
	 * Create the table
	 */
	void create();
	
	/**
	 * Drop the table
	 */
	@SqlUpdate("DROP TABLE `<prefix>schema_history`")
	void drop();
	
	/**
	 * Insert a new script into the table
	 *
	 * @param version the version
	 * @param description the description
	 * @param script the script file name
	 * @param install the unix timestamp of the install date
	 */
	@SqlUpdate("INSERT INTO `<prefix>schema_history` (`version`, `description`, `script_name`, `install_date`) VALUES (?, ?, ?, ?)")
	void insert(int version, String description, String script, long install);
	
	/**
	 * Get the higher version in the table
	 *
	 * @return the higher version
	 */
	@SqlQuery("SELECT max(`version`) FROM `<prefix>schema_history`")
	int higherVersion();
	
	/**
	 * Count how many scripts are saved in the table
	 *
	 * @return the number of scripts
	 */
	@SqlQuery("SELECT count(*) FROM `<prefix>schema_history`")
	int countVersions();
}
