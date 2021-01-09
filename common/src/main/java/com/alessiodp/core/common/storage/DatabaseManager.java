package com.alessiodp.core.common.storage;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.configuration.Constants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class DatabaseManager {
	protected final ADPPlugin plugin;
	protected final ExecutorService databaseExecutor;
	protected IDatabaseDispatcher database;
	@Getter @Setter private StorageType databaseType;
	
	public DatabaseManager(@NonNull ADPPlugin plugin) {
		this.plugin = plugin;
		
		databaseExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
				.setNameFormat(plugin.getPluginFallbackName() + "-database-pool")
				.setDaemon(true)
				.setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
				.build()
		);
	}
	
	/**
	 * Initialize database manager
	 *
	 * @param storageType the storage type to initialize
	 * @return the database dispatcher initialized
	 */
	protected IDatabaseDispatcher init(StorageType storageType) {
		// Initialize libraries
		if (!storageType.initLibraries(plugin)) {
			plugin.getLoggerManager().printError(String.format(Constants.DEBUG_DB_INIT_FAILED_LIBRARIES, storageType.getFormattedName()));
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
	
	protected CompletableFuture<Void> executeSafelyAsync(Runnable runnable) {
		return CompletableFuture.runAsync(runnable, databaseExecutor);
	}
	
	protected <T> CompletableFuture<T> executeSafelySupplyAsync(Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(supplier, databaseExecutor);
	}
	
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
		
		plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_DB_INIT, getDatabaseType().getFormattedName()), true);
		
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
