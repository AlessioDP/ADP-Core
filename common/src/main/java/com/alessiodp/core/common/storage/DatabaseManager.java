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
	 */
	@Getter @Setter private StorageType databaseType;
	
	/**
	 * The plugin is shutting down?
 	 */
	@Getter private boolean shuttingDown = false;
	
	/**
	 * Reload database manager
	 */
	public void reload() {
		// Stop if already initialized
		stop();
		
		// Check if storages are valid
		if (getDatabaseType() == null) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_NOTFOUND);
			shuttingDown = true;
			return;
		}
		
		plugin.getLoggerManager().logDebug(Constants.DEBUG_DB_INIT
				.replace("{db}", getDatabaseType().getFormattedName()), true);
		
		// Initialize storages
		database = initializeDispatcher(getDatabaseType());
		
		// Check if something gone wrong, if so stop the plugin
		if (database == null) {
			shuttingDown = true;
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
	
	/**
	 * Initialize database dispatcher
	 *
	 * @param storageType the storage type to initialize
	 * @return the database dispatcher initialized
	 */
	public abstract IDatabaseDispatcher initializeDispatcher(StorageType storageType);
}
