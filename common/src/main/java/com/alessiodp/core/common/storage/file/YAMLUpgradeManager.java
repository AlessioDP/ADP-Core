package com.alessiodp.core.common.storage.file;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class YAMLUpgradeManager {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final YAMLDao databaseFile;
	
	public void checkForUpgrades() {
		int version = databaseFile.getYaml().getInt("database-version", -1);
		if (version >= 0) {
			upgradeDatabase(version);
		}
	}
	
	/**
	 * Upgrade the database
	 *
	 * @param currentVersion the current version of the database
	 */
	protected abstract void upgradeDatabase(int currentVersion);
}
