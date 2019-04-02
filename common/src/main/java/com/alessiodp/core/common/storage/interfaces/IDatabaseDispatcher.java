package com.alessiodp.core.common.storage.interfaces;

import com.alessiodp.core.common.storage.StorageType;

public interface IDatabaseDispatcher {
	/**
	 * Initialize the database dispatcher
	 *
	 * @param type the storage type that must be initialized
	 */
	void init(StorageType type);
	
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
