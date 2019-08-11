package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.adapter.ConfigurationAdapter;
import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Files;
import java.nio.file.Path;
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
			Path cfPath = cf.saveDefaultConfiguration(plugin.getFolder());
			
			// Initialize ConfigurationAdapter
			ConfigurationAdapter cfCA = initializeConfigurationAdapter(cfPath);
			
			// Check version
			cf.setOutdated(cf.checkVersion(cfCA));
			
			// Load configuration
			if (Files.exists(cfPath))
				cf.loadConfiguration(cfCA);
		}
		
		performChanges();
	}
	
	/**
	 * Initialize configuration adapter
	 *
	 * @param configurationFile the configuration path to initialize
	 * @return a new configuration adapter
	 */
	protected abstract ConfigurationAdapter initializeConfigurationAdapter(@NonNull Path configurationFile);
	
	/**
	 * Perform any change to the settings or other classes at the end of configuration load
	 */
	protected abstract void performChanges();
	
	protected void checkOutdatedConfigs(String outdatedMessage) {
		// Check if outdated
		for (ConfigurationFile cf : configs) {
			if (cf.isOutdated()) {
				plugin.getLoginAlertsManager().getLoginAlerts().add(outdatedMessage
						.replace("%config%", cf.getFileName()));
				
				plugin.getLoggerManager().printError(Constants.DEBUG_CONFIG_OUTDATED
						.replace("{name}", cf.getFileName()));
			}
		}
	}
}
