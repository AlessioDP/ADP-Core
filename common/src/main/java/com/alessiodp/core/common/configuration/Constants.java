package com.alessiodp.core.common.configuration;

public class Constants {
	// Libraries
	public static final String LIBRARY_FOLDER = "libs/";
	public static final String LIBRARY_URL = "https://repo1.maven.org/maven2/%package%/%name%/%version%/%file%";
	
	// Database
	public static final String DATABASE_SCHEMA_DIVIDER = "\\/\\*START\\_([a-z]+)\\*\\/([^\\/\\*]*)\\/\\*END\\_\\1\\*\\/";
	
	// Updater
	public static final String UPDATER_FOUND = "{plugin} v{currentVersion} found a new version: {newVersion}";
	public static final String UPDATER_FAILED_IO = "{plugin} could not contact alessiodp.com for updating.";
	public static final String UPDATER_FAILED_GENERAL = "{plugin} could not check for updates.";
	public static final String UPDATER_URL = "https://api.alessiodp.com/version.php?plugin={plugin}&version={version}";
	public static final String UPDATER_FALLBACK_URL = "https://www.spigotmc.org/api/general.php";
	public static final String UPDATER_FALLBACK_KEY = "98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4";
	public static final String UPDATER_FALLBACK_WARN = "{plugin} will check updates manually via Spigot API.";
	public static final String UPDATER_FIELD_VERSION = "version";
	public static final String UPDATER_DELIMITER_TYPE = "\\-";
	public static final String UPDATER_DELIMITER_VERSION = "\\.";
	
	// Common messages
	public static final String ONLY_PLAYERS = "You must be a player to use this command.";
	
	// Common SQL queries
	public static final String QUERY_CHECKVERSION_SET_MYSQL = "INSERT INTO {table_versions} (`name`, `version`) VALUES (?,?) ON DUPLICATE KEY UPDATE `name`=VALUES(`name`), `version`=VALUES(`version`);";
	public static final String QUERY_CHECKVERSION_SET_SQLITE = "INSERT OR REPLACE INTO {table_versions} (`name`, `version`) VALUES (?,?);";
	public static final String QUERY_RENAME_MYSQL = "RENAME TABLE {table} TO {newtable};";
	public static final String QUERY_RENAME_SQLITE = "ALTER TABLE {table} RENAME TO {newtable};";
	public static final String QUERY_GENERIC_SELECTALL = "SELECT * FROM {table};";
	public static final String QUERY_GENERIC_DROP = "DROP TABLE {table};";
	
	// Debug messages
	public static final String DEBUG_PLUGIN_ENABLING = "Initializing {plugin} {version}";
	public static final String DEBUG_PLUGIN_ENABLED = "{plugin} v{version} enabled";
	public static final String DEBUG_PLUGIN_DISABLING = "Disabling {plugin}";
	public static final String DEBUG_PLUGIN_DISABLED = "{plugin} disabled";
	public static final String DEBUG_PLUGIN_DISABLED_LOG = "========== {plugin} disabled - End of Log ==========";
	
	public static final String DEBUG_ADDON_INIT = "Initializing addons...";
	public static final String DEBUG_ADDON_HOOKED = "Hooked into {addon}";
	public static final String DEBUG_ADDON_FAILED = "Failed to hook into {addon}, disabled its features";
	public static final String DEBUG_ADDON_OUTDATED = "Failed to hook into {addon}, you are using an old version of that plugin";
	
	public static final String DEBUG_CMD_SETUP_PREPARE = "Preparing commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER = "Registering commands...";
	public static final String DEBUG_CMD_SETUP_REGISTER_SUBCOMMAND = "Registering sub command '{sub}' for '{main}'";
	public static final String DEBUG_CMD_SETUP_SETUP = "Setting up commands...";
	public static final String DEBUG_CMD_SETUP_ORDER = "Ordering commands...";
	public static final String DEBUG_CMD_SETUP_FAILED = "Failed to register command {command}";
	public static final String DEBUG_CMD_SETUP_OVERWRITTEN = "'{command}' was registered by another plugin, trying to overwrite it. If you have any problem you should change it";
	
	public static final String DEBUG_CONFIG_OUTDATED = "The file {name} is outdated!";
	public static final String DEBUG_CONFIG_SAVE_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	
	public static final String DEBUG_DB_INIT = "Trying to initialize database '{db}'";
	public static final String DEBUG_DB_INIT_FAILED = "Failed to initialize the storage, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_NOTFOUND = "Failed to found a valid storage type, stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_UNSUPPORTED = "Unsupported storage type '{type}', stopping plugin!";
	public static final String DEBUG_DB_INIT_FAILED_MYSQL = "Failed to initialize MySQL driver: {message}";
	public static final String DEBUG_DB_FILE_CREATEFAIL = "Failed to create data file: {type} > {message}";
	public static final String DEBUG_DB_FILE_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	
	public static final String DEBUG_LIB_INIT_INIT = "Initializing library '{lib}' v{version}";
	public static final String DEBUG_LIB_INIT_DL = "Downloading library '{lib}' v{version}";
	public static final String DEBUG_LIB_INIT_LOAD = "Loading library '{lib}' v{version}";
	public static final String DEBUG_LIB_INIT_FAIL = "Cannot load library '{lib}' v{version}";
	public static final String DEBUG_LIB_FAILED_DL = "Failed to download the library '{lib}' v{version}: {message}";
	public static final String DEBUG_LIB_FAILED_LOAD = "Failed to download the library '{lib}' v{version}:";
	
	public static final String DEBUG_LOG_RELOADED = "Logger manager reloaded";
	
	public static final String DEBUG_SCHEDULER_SHUTDOWN = "Shutting down scheduler...";
	
	public static final String DEBUG_SQL_FAILED = "Failed initialization of {type}, error: {type} > {message}";
	public static final String DEBUG_SQL_SCHEMA_INIT = "Handling {class} schema";
	public static final String DEBUG_SQL_SCHEMA_FOUND = "Found schema: {schema}";
	public static final String DEBUG_SQL_CONNECTIONERROR = "Failed to connect to {storage}: {message}";
	public static final String DEBUG_SQL_ERROR = "Error in {class} at {method}_{line}: {type} > {message} \n{stacktrace}";
	public static final String DEBUG_SQL_ERROR_TABLE = "Error in {class} at {method}({table})_{line}: {type} > {message} \n{stacktrace}";
	public static final String DEBUG_SQL_ERROR_UUID = "Failed to parse uuid '{uuid}' at {method}_{line}: {type} > {message}";
	public static final String DEBUG_SQL_UPGRADING = "Upgrading the SQL table '{table}'";
}
