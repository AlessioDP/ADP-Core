package com.alessiodp.core.common.storage;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class DatabaseManager {
	protected final ADPPlugin plugin;
	
	/**
	 * Active database dispatcher
	 */
	protected IDatabaseDispatcher database;
	
	/**
	 * Active database type
	 *
	 * @param databaseType the storage to set
	 * @return the storage type
	 */
	@Getter @Setter private StorageType databaseType;
	
	/**
	 * Initialize database manager
	 *
	 * @param storageType the storage type to initialize
	 * @return the database dispatcher initialized
	 */
	protected IDatabaseDispatcher init(StorageType storageType) {
		// Initialize libraries
		if (!storageType.initLibraries(plugin)) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_LIBRARIES
					.replace("{type}", storageType.getFormattedName()));
			return null;
		}
		
		// Initialize the correct dispatcher (File/SQL)
		IDatabaseDispatcher ret = initializeDispatcher(storageType);
		
		// Check if supported
		if (ret != null) {
			// Initialize it
			ret.init();
			if (ret.isFailed())
				return null;
		}
		return ret;
	}
	
	/**
	 * Initialize database dispatcher
	 *
	 * @param storageType the storage type to initialize
	 * @return the database dispatcher initialized
	 */
	protected abstract IDatabaseDispatcher initializeDispatcher(StorageType storageType);
	
	/**
	 * Reload database manager
	 */
	public void reload() {
		// Stop if already initialized
		stop();
		
		// Check if storages are valid
		if (getDatabaseType() == null) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_NOTFOUND);
			plugin.setPluginDisabled(true);
			return;
		}
		
		plugin.getLoggerManager().logDebug(Constants.DEBUG_DB_INIT
				.replace("{db}", getDatabaseType().getFormattedName()), true);
		
		// Initialize storages
		database = init(getDatabaseType());
		
		// Check if something gone wrong, if so stop the plugin
		if (database == null) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED);
			plugin.setPluginDisabled(true);
		}
	}
	
	/**
	 * Stop all databases
	 */
	public final void stop() {
		if (database != null) {
			database.stop();
		}
	}
}
