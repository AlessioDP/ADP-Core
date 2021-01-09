package com.alessiodp.core.common.storage.sql.connection;

import com.alessiodp.core.common.storage.StorageType;
import org.jdbi.v3.core.Jdbi;

public interface ConnectionFactory {
	/**
	 * Initialize connection factory
	 */
	void init();
	
	/**
	 * Stop the connection factory
	 */
	void stop();
	
	/**
	 * Is the connection factory failed to start?
	 *
	 * @return true if the initialization has failed
	 */
	boolean isFailed();
	
	/**
	 * Get the instance of JDBI
	 *
	 * @return the JDBI instance, null before initialization
	 */
	Jdbi getJdbi();
	
	/**
	 * Get the storage type of the connection factory
	 *
	 * @return the storage type
	 */
	StorageType getType();
	
	/**
	 * Get the table prefix
	 *
	 * @return the table prefix
	 */
	String getTablePrefix();
	
	/**
	 * Set the database url of the connection factory
	 *
	 * @param databaseUrl the database url to set
	 */
	void setDatabaseUrl(String databaseUrl);
}
