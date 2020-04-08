package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.file.FileUpgradeManager;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.storage.interfaces.IDatabaseFile;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FileDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final StorageType storageType;
	
	protected IDatabaseFile database;
	protected FileUpgradeManager upgradeManager;
	
	@Override
	public final void stop() {
		// Nothing to do
	}
	
	@Override
	public final boolean isFailed() {
		return database == null || database.isFailed();
	}
	
	@Override
	public void init() {
		// Initialize DAO
		database = initDao();
		
		// Check if initialized
		if (database != null) {
			database.initFile();
			
			// Check for failures
			if (database != null && !database.isFailed()) {
				upgradeManager = initUpgradeManager();
				upgradeManager.checkForUpgrades();
			}
		}
	}
	
	/**
	 * Initialize the file DAO
	 *
	 * @return the initialized DAO
	 */
	protected abstract IDatabaseFile initDao();
	
	/**
	 * Initialize the upgradee manager
	 *
	 * @return the initialized upgrade manager
	 */
	protected abstract FileUpgradeManager initUpgradeManager();
}
