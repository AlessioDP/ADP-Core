package com.alessiodp.core.common.storage.interfaces;

public interface IDatabaseDispatcher {
	/**
	 * Initialize the database dispatcher
	 */
	void init();
	
	/**
	 * Stop the database dispatcher
	 */
	void stop();
	
	/**
	 * Is failed to start the database?
	 *
	 * @return true if the initialization has failed
	 */
	boolean isFailed();
}
