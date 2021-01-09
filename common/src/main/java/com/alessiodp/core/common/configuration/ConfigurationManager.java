package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigurationManager {
	protected final ADPPlugin plugin;
	@Getter private final List<ConfigurationFile> configs = new ArrayList<>();
	
	public ConfigurationManager(@NonNull ADPPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Reload configuration files
	 */
	public final void reload() {
		// Load defaults
		for (ConfigurationFile cf : configs) {
			// Load defaults
			cf.loadDefaults();
			
			// Initialize config
			cf.initializeConfiguration(plugin.getFolder());
			
			// Check version
			cf.setOutdated(cf.checkVersion());
			
			// Load configuration
			if (cf.exists())
				cf.loadConfiguration();
		}
		
		performChanges();
	}
	
	/**
	 * Perform any change to the settings or other classes at the end of configuration load
	 */
	protected abstract void performChanges();
	
	/**
	 * Check for outdated configs
	 *
	 * @param outdatedMessage The outdated message to print
	 */
	protected void checkOutdatedConfigs(String outdatedMessage) {
		// Check if outdated
		for (ConfigurationFile cf : configs) {
			if (cf.isOutdated()) {
				plugin.getLoginAlertsManager().getLoginAlerts().add(outdatedMessage
						.replace("%config%", cf.getFileName()));
				
				plugin.getLoggerManager().printError(String.format(Constants.DEBUG_CONFIG_OUTDATED, cf.getFileName()));
			}
		}
	}
}
