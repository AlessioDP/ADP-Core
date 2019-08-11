package com.alessiodp.core.common;

import com.alessiodp.core.common.addons.AddonManager;
import com.alessiodp.core.common.addons.internal.JsonHandler;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.bootstrap.AbstractADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import com.alessiodp.core.common.addons.internal.ADPUpdater;
import com.alessiodp.core.common.addons.libraries.LibraryManager;
import com.alessiodp.core.common.commands.CommandManager;
import com.alessiodp.core.common.configuration.ConfigurationManager;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.messaging.ADPMessenger;
import com.alessiodp.core.common.players.LoginAlertsManager;
import com.alessiodp.core.common.scheduling.ADPScheduler;
import com.alessiodp.core.common.storage.DatabaseManager;
import com.alessiodp.core.common.utils.IColorUtils;
import com.alessiodp.core.common.utils.IPlayerUtils;
import lombok.Getter;

public abstract class ADPPlugin extends AbstractADPPlugin {
	// Plugin fields
	@Getter private static ADPPlugin instance;
	
	// Common fields
	@Getter protected AddonManager addonManager;
	@Getter protected CommandManager commandManager;
	@Getter protected ConfigurationManager configurationManager;
	@Getter protected DatabaseManager databaseManager;
	@Getter protected LibraryManager libraryManager;
	@Getter protected LoggerManager loggerManager;
	@Getter protected LoginAlertsManager loginAlertsManager;
	@Getter protected ADPMessenger messenger;
	@Getter protected ADPScheduler scheduler;
	
	// Utils
	@Getter protected ADPUpdater adpUpdater;
	@Getter protected IColorUtils colorUtils;
	@Getter protected JsonHandler jsonHandler;
	@Getter protected IPlayerUtils playerUtils;
	
	protected ADPPlugin(ADPBootstrap bootstrap) {
		super(bootstrap);
	}
	
	/**
	 * On plugin enable
	 */
	public void enabling() {
		// Init
		instance = this;
		logConsole(Constants.DEBUG_PLUGIN_ENABLING
				.replace("{plugin}", this.getPluginName())
				.replace("{version}", this.getVersion()), false);
		
		// Pre-handle
		preHandle();
		
		// Handle
		handle();
		
		// Check if plugin failed to start
		if (!getDatabaseManager().isShuttingDown()) {
			postHandle();
			
			getLoggerManager().log(Constants.DEBUG_PLUGIN_ENABLED
					.replace("{plugin}", this.getPluginName())
					.replace("{version}", this.getVersion()), true);
		}
	}
	
	/**
	 * On plugin disable
	 */
	public void disabling() {
		logConsole(Constants.DEBUG_PLUGIN_DISABLING
				.replace("{plugin}", this.getPluginName()), false);
		
		onDisabling();
		
		if (databaseManager != null && !databaseManager.isShuttingDown()) {
			// This is not a force close
			getDatabaseManager().stop();
		}
		
		scheduler.shutdown();
		
		getLoggerManager().log(Constants.DEBUG_PLUGIN_DISABLED_LOG
				.replace("{plugin}", getPluginName()), false);
		
		logConsole(Constants.DEBUG_PLUGIN_DISABLED
				.replace("{plugin}", this.getPluginName()), false);
	}
	
	/**
	 * Used to disable other non-common managers
	 */
	public abstract void onDisabling();
	
	/**
	 * Preparation before the main handle method
	 * Initialize necessary classes
	 */
	protected final void preHandle() {
		loggerManager = new LoggerManager(this);
		libraryManager = new LibraryManager(this);
		loginAlertsManager = new LoginAlertsManager();
		initializeCore();
	}
	
	/**
	 * Load necessaries classes and initialize others
	 */
	protected final void handle() {
		loadCore();
		if (getDatabaseManager().isShuttingDown()) {
			// Storage error, shutdown plugin
			getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED);
			super.getBootstrap().stopPlugin();
		}
		
		adpUpdater = new ADPUpdater(this);
		initializeJsonHandler();
	}
	
	/**
	 * Load any other class
	 */
	protected abstract void postHandle();
	
	/**
	 * Used to initialize: scheduler, database and configuration
	 */
	protected abstract void initializeCore();
	
	/**
	 * Use to load: scheduler, database and configuration
	 */
	protected abstract void loadCore();
	
	/**
	 * Reload the configuration
	 */
	public abstract void reloadConfiguration();
	
	/**
	 * Initialize Json Handler instance
	 */
	protected abstract void initializeJsonHandler();
}
