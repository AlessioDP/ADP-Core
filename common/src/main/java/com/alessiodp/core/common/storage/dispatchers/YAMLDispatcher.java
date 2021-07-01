package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.file.YAMLDao;
import com.alessiodp.core.common.storage.file.YAMLUpgradeManager;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class YAMLDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	
	@Getter protected YAMLDao database;
	protected YAMLUpgradeManager upgradeManager;
	
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
			if (!isFailed()) {
				upgradeManager = initUpgradeManager();
				if (upgradeManager != null)
					upgradeManager.checkForUpgrades();
			}
		}
	}
	
	/**
	 * Initialize the file DAO
	 *
	 * @return the initialized DAO
	 */
	protected abstract YAMLDao initDao();
	
	/**
	 * Initialize the upgrade manager
	 *
	 * @return the initialized upgrade manager
	 */
	protected abstract YAMLUpgradeManager initUpgradeManager();
	
	/**
	 * Save the database
	 *
	 * @return Returns true if successfully saved the database
	 */
	protected boolean save() {
		try {
			database.saveFile();
			return true;
		} catch (IOException ex) {
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_DB_FILE_ERROR, ex);
		}
		return false;
	}
}
