package com.alessiodp.core.common.configuration;

public class Constants {
	
	// Updater
	public static final String UPDATER_FOUND = "%s v%s found a new version: %s";
	public static final String UPDATER_FAILED_ADP = "%s could not contact alessiodp.com to check for updates.";
	public static final String UPDATER_FAILED_SPIGOT = "%s could not contact spigotmc.org to check for updates.";
	public static final String UPDATER_URL = "https://api.alessiodp.com/version.php?plugin={plugin}&version={version}";
	public static final String UPDATER_FALLBACK_URL = "https://api.spigotmc.org/legacy/update.php?resource={id}";
	public static final String UPDATER_FALLBACK_WARN = "%s will manually check for updates via Spigot API.";
	public static final String UPDATER_FIELD_VERSION = "version";
	public static final String UPDATER_DELIMITER_TYPE = "\\-";
	public static final String UPDATER_DELIMITER_VERSION = "\\.";
	
	// Debug messages
	public static final String DEBUG_PLUGIN_LOADING = "Loading libraries of %s v%s, this may take a while";
	public static final String DEBUG_PLUGIN_LOADING_FAILED = "Failed to load libraries, the plugin will be stopped";
	public static final String DEBUG_PLUGIN_ENABLING = "Initializing %s v%s";
	public static final String DEBUG_PLUGIN_ENABLED = "%s v%s enabled";
	public static final String DEBUG_PLUGIN_DISABLING = "Disabling %s";
	public static final String DEBUG_PLUGIN_DISABLED = "%s disabled";
	public static final String DEBUG_PLUGIN_DISABLED_LOG = "========== %s disabled - End of Log ==========";
	public static final String DEBUG_PLUGIN_REGISTERING = "Registering listeners...";
	public static final String DEBUG_PLUGIN_RELOADING = "Reloading plugin...";
	
	public static final String DEBUG_ADDON_INIT = "Initializing addons...";
	public static final String DEBUG_ADDON_HOOKED = "Hooked into %s";
	public static final String DEBUG_ADDON_FAILED = "Failed to hook into %s, disabled its features";
	public static final String DEBUG_ADDON_OUTDATED = "Failed to hook into %s, you are using an old version of that plugin";
	public static final String DEBUG_ADDON_METRICS_INIT = "Initializing metrics handler...";
	
	public static final String DEBUG_CMD_SETUP_PREPARE = "Preparing commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER = "Registering commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER_SUBCOMMAND = "Registering sub command '%s' for '%s'";
	public static final String DEBUG_CMD_SETUP_SETUP = "Setting up commands...";
	public static final String DEBUG_CMD_SETUP_ORDER = "Ordering commands...";
	public static final String DEBUG_CMD_SETUP_FAILED = "Failed to register command %s";
	public static final String DEBUG_CMD_SETUP_OVERWRITTEN = "'%s' was registered by another plugin, trying to overwrite it. If you have any problem you should change it";
	public static final String DEBUG_CMD_EXECUTION = "%s performed the command '%s' with %d arguments";
	
	public static final String DEBUG_CONFIG_OUTDATED = "The file %s is outdated!";
	public static final String DEBUG_CONFIG_NOTFOUND = "The configuration key '%s' in '%s' was not found, configuration outdated?";
	public static final String DEBUG_CONFIG_SAVE_ERROR = "Error in %s at %s_%d: %s > %s \n%s";
	
	public static final String DEBUG_DB_INIT = "Trying to initialize database '%s'";
	public static final String DEBUG_DB_INIT_FAILED = "Failed to initialize the storage, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_NOTFOUND = "Failed to found a valid storage type, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_UNSUPPORTED = "Unsupported storage type '%s', stopping plugin!";
	public static final String DEBUG_DB_QUERY_EXECUTE = "Executing query: %s";
	public static final String DEBUG_DB_FILE_CREATEFAIL = "Failed to create YAML data file: %s";
	public static final String DEBUG_DB_FILE_ERROR = DEBUG_CONFIG_SAVE_ERROR;
	public static final String DEBUG_DB_MIGRATOR_CREATING_TABLE = "Creating schema history table";
	public static final String DEBUG_DB_MIGRATOR_MIGRATING = "Migrating database with '%s'";
	
	public static final String DEBUG_LOG_LOADED = "Logger manager loaded";
	
	public static final String DEBUG_LOG_MESSAGING_SENT = "Sent a packet of type '%s' via the player %s via the channel '%s'";
	public static final String DEBUG_LOG_MESSAGING_SENT_FAILED = "Cannot send a packet of type '%s' due to missing players";
	public static final String DEBUG_LOG_MESSAGING_FAILED_SEND = "Failed to send a plugin message: %s";
	public static final String DEBUG_LOG_MESSAGING_FAILED_VERSION = "Failed to read packet because version mismatching: %s != %s";
	public static final String DEBUG_LOG_MESSAGING_FAILED_READ = "Failed to read the packet: %s";
}
