package com.alessiodp.core.common.configuration;

public class Constants {
	
	// Updater
	public static final String UPDATER_FOUND = "{plugin} v{currentVersion} found a new version: {newVersion}";
	public static final String UPDATER_FAILED_ADP = "{plugin} could not contact alessiodp.com to check for updates.";
	public static final String UPDATER_FAILED_SPIGOT = "{plugin} could not contact spigotmc.org to check for updates.";
	public static final String UPDATER_URL = "https://api.alessiodp.com/version.php?plugin={plugin}&version={version}";
	public static final String UPDATER_FALLBACK_URL = "https://api.spigotmc.org/legacy/update.php?resource={id}";
	public static final String UPDATER_FALLBACK_WARN = "{plugin} will manually check for updates via Spigot API.";
	public static final String UPDATER_FIELD_VERSION = "version";
	public static final String UPDATER_DELIMITER_TYPE = "\\-";
	public static final String UPDATER_DELIMITER_VERSION = "\\.";
	
	
	// Common messages
	public static final String ONLY_PLAYERS = "You must be a player to use this command.";
	
	
	// Debug messages
	public static final String DEBUG_PLUGIN_ENABLING = "Initializing {plugin} {version}";
	public static final String DEBUG_PLUGIN_ENABLED = "{plugin} v{version} enabled";
	public static final String DEBUG_PLUGIN_DISABLING = "Disabling {plugin}";
	public static final String DEBUG_PLUGIN_DISABLED = "{plugin} disabled";
	public static final String DEBUG_PLUGIN_DISABLED_LOG = "========== {plugin} disabled - End of Log ==========";
	public static final String DEBUG_PLUGIN_REGISTERING = "Registering listeners...";
	public static final String DEBUG_PLUGIN_RELOADING = "Reloading plugin...";
	
	public static final String DEBUG_ADDON_INIT = "Initializing addons...";
	public static final String DEBUG_ADDON_HOOKED = "Hooked into {addon}";
	public static final String DEBUG_ADDON_FAILED = "Failed to hook into {addon}, disabled its features";
	public static final String DEBUG_ADDON_OUTDATED = "Failed to hook into {addon}, you are using an old version of that plugin";
	public static final String DEBUG_ADDON_METRICS_INIT = "Initializing metrics handler...";
	
	public static final String DEBUG_CMD_SETUP_PREPARE = "Preparing commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER = "Registering commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER_SUBCOMMAND = "Registering sub command '{sub}' for '{main}'";
	public static final String DEBUG_CMD_SETUP_SETUP = "Setting up commands...";
	public static final String DEBUG_CMD_SETUP_ORDER = "Ordering commands...";
	public static final String DEBUG_CMD_SETUP_FAILED = "Failed to register command {command}";
	public static final String DEBUG_CMD_SETUP_OVERWRITTEN = "'{command}' was registered by another plugin, trying to overwrite it. If you have any problem you should change it";
	
	public static final String DEBUG_CONFIG_OUTDATED = "The file {name} is outdated!";
	public static final String DEBUG_CONFIG_NOTFOUND = "The configuration key '{key}' in '{config}' was not found, configuration outdated?";
	public static final String DEBUG_CONFIG_SAVE_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	
	public static final String DEBUG_DB_INIT = "Trying to initialize database '{db}'";
	public static final String DEBUG_DB_INIT_FAILED = "Failed to initialize the storage, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_NOTFOUND = "Failed to found a valid storage type, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_UNSUPPORTED = "Unsupported storage type '{type}', stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_LIBRARIES = "Failed to download required libraries for '{type}', stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_DRIVER = "Failed to initialize {storage} driver: {message}";
	public static final String DEBUG_DB_QUERY_EXECUTE = "Executing query: {query}";
	public static final String DEBUG_DB_FILE_CREATEFAIL = "Failed to create data file: {type} > {message}";
	public static final String DEBUG_DB_FILE_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	public static final String DEBUG_DB_MIGRATOR_CREATING_TABLE = "Creating schema history table";
	public static final String DEBUG_DB_MIGRATOR_MIGRATING = "Migrating database with '{file}'";
	
	public static final String DEBUG_LOG_LOADED = "Logger manager loaded";
	
	public static final String DEBUG_LOG_MESSAGING_FAILED_SEND = "Failed to send a plugin message: {message}";
	public static final String DEBUG_LOG_MESSAGING_FAILED_VERSION = "Failed to read packet because version mismatching: {current} != {version}";
	public static final String DEBUG_LOG_MESSAGING_FAILED_READ = "Failed to read the packet: {message}";
	
	public static final String DEBUG_SCHEDULER_SHUTDOWN = "Shutting down scheduler...";
	
	public static final String DEBUG_SQL_FAILED = "Failed initialization of {type}, error: {type} > {message}";
	public static final String DEBUG_SQL_CONNECTIONERROR = "Failed to connect to {storage}: {message}";
	public static final String DEBUG_SQL_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	public static final String DEBUG_SQL_ERROR_TABLE = "Error in {class} at {method}({table})_{line}: {type} > {message} \n{stacktrace}";
	public static final String DEBUG_SQL_ERROR_UUID = "Failed to parse uuid '{uuid}' at {method}_{line}: {type} > {message}";
}
