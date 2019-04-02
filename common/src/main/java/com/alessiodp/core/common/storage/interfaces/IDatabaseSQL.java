package com.alessiodp.core.common.storage.interfaces;

import java.sql.Connection;

public interface IDatabaseSQL {
	
	/**
	 * Initialize the sql database dispatcher
	 */
	void initSQL();
	
	/**
	 * Stop the sql database dispatcher
	 */
	void stopSQL();
	
	/**
	 * Get a connection from the pool
	 *
	 * @return the connection initialized
	 */
	Connection getConnection();
	
	/**
	 * Is the database failed to start?
	 *
	 * @return true if the initialization has failed
	 */
	boolean isFailed();
}