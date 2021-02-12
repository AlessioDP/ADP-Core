package com.alessiodp.core.common.storage.sql.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaHistoryDao {
	String QUERY_DROP = "DROP TABLE IF EXISTS `<prefix>schema_history`";
	String QUERY_INSERT = "INSERT INTO `<prefix>schema_history` (`version`, `description`, `script_name`, `install_date`) VALUES (?, ?, ?, ?)";
	String QUERY_HIGHER_VERSION = "SELECT max(`version`) FROM `<prefix>schema_history`";
	String QUERY_COUNT_VERSIONS = "SELECT count(*) FROM `<prefix>schema_history`";
	
	/**
	 * Create the table
	 */
	void create();
	
	/**
	 * Drop the table
	 */
	@SqlUpdate(QUERY_DROP)
	void drop();
	
	/**
	 * Insert a new script into the table
	 *
	 * @param version the version
	 * @param description the description
	 * @param script the script file name
	 * @param install the unix timestamp of the install date
	 */
	@SqlUpdate(QUERY_INSERT)
	void insert(int version, String description, String script, long install);
	
	/**
	 * Get the higher version in the table
	 *
	 * @return the higher version
	 */
	@SqlQuery(QUERY_HIGHER_VERSION)
	int higherVersion();
	
	/**
	 * Count how many scripts are saved in the table
	 *
	 * @return the number of scripts
	 */
	@SqlQuery(QUERY_COUNT_VERSIONS)
	int countVersions();
}
