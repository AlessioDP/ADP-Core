package com.alessiodp.core.common.storage.sql;

public interface ISQLTable {
	/**
	 * Get the table type
	 *
	 * @return the type of the table
	 */
	String getTypeName();
	
	/**
	 * Get the table name
	 *
	 * @return the name of the table
	 */
	String getTableName();
	
	/**
	 * Get the query to create the table
	 *
	 * @return the create query of the table
	 */
	String getCreateQuery();
	
	/**
	 * Get the version of the table
	 *
	 * @return the version number of the table
	 */
	int getVersion();
	
	/**
	 * Format a query for the table
	 *
	 * @param query the query that must be formatted
	 * @return the formatted query
	 */
	String formatQuery(String query);
}
