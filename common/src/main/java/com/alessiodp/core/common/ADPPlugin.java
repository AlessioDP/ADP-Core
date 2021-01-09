package com.alessiodp.core.common;

import com.alessiodp.core.common.addons.AddonManager;
import com.alessiodp.core.common.addons.internal.JsonHandler;
import com.alessiodp.core.common.addons.internal.TitleHandler;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.bootstrap.AbstractADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import com.alessiodp.core.common.addons.internal.ADPUpdater;
import com.alessiodp.core.common.commands.CommandManager;
import com.alessiodp.core.common.configuration.ConfigurationManager;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.messaging.ADPMessenger;
import com.alessiodp.core.common.players.LoginAlertsManager;
import com.alessiodp.core.common.scheduling.ADPScheduler;
import com.alessiodp.core.common.storage.DatabaseManager;
import com.alessiodp.core.common.utils.IPlayerUtils;
import lombok.Getter;
import lombok.Setter;

public abstract class ADPPlugin extends AbstractADPPlugin {
	// Plugin fields
	@Getter private static ADPPlugin instance;
	@Getter @Setter private boolean isPluginDisabled;
	
	// Common fields
	@Getter protected AddonManager addonManager;
	@Getter protected CommandManager commandManager;
	@Getter protected ConfigurationManager configurationManager;
	@Getter protected DatabaseManager databaseManager;
	@Getter protected LoggerManager loggerManager;
	@Getter protected LoginAlertsManager loginAlertsManager;
	@Getter protected ADPMessenger messenger;
	@Getter protected ADPScheduler scheduler;
	
	// Utils
	@Getter protected ADPUpdater adpUpdater;
	@Getter protected JsonHandler jsonHandler;
	@Getter protected TitleHandler titleHandler;
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
		isPluginDisabled = false;
		logConsole(String.format(Constants.DEBUG_PLUGIN_ENABLING, this.getPluginName(), this.getVersion()), false);
		
		// Pre handle
		preHandle();
		
		// Handle
		if (!isPluginDisabled)
			handle();
		
		// Post handle
		if (!isPluginDisabled)
			postHandle();
		
		if (isPluginDisabled) {
			super.getBootstrap().stopPlugin();
		}
		
		getLoggerManager().log(String.format(Constants.DEBUG_PLUGIN_ENABLED, this.getPluginName(), this.getVersion()), true);
	}
	
	/**
	 * On plugin disable
	 */
	public void disabling() {
		logConsole(String.format(Constants.DEBUG_PLUGIN_DISABLING, this.getPluginName()), false);
		
		onDisabling();
		
		if (databaseManager != null) {
			// This is not a force close
			getDatabaseManager().stop();
		}
		
		scheduler.shutdown();
		
		getLoggerManager().log(String.format(Constants.DEBUG_PLUGIN_DISABLED_LOG, getPluginName()), false);
		
		logConsole(String.format(Constants.DEBUG_PLUGIN_DISABLED, this.getPluginName()), false);
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
		loginAlertsManager = new LoginAlertsManager();
		initializeCore();
	}
	
	/**
	 * Load necessaries classes and initialize others
	 */
	protected final void handle() {
		loadCore();
		
		adpUpdater = new ADPUpdater(this);
		initializeJsonHandler();
		initializeTitleHandler();
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
	
	/**
	 * Initialize Title Handler instance
	 */
	protected abstract void initializeTitleHandler();
}
