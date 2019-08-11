package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.adapter.ConfigurationAdapter;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public abstract class ConfigurationFile {
	@NonNull protected final ADPPlugin plugin;
	@Getter @Setter private boolean outdated = false;
	
	/**
	 * Save default configuration to path
	 *
	 * @param path the file path
	 * @return the same path
	 */
	public Path saveDefaultConfiguration(@NonNull Path path) {
		Path ret = path.resolve(getFileName());
		try {
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			if (!Files.exists(ret)) {
				byte[] data = ByteStreams.toByteArray(plugin.getResource(getResourceName()));
				
				Files.write(ret, data);
			}
		} catch (Exception ex) {
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_CONFIG_SAVE_ERROR, ex);
		}
		return ret;
	}
	
	/**
	 * Check the version of the configuration
	 *
	 * @param confAdapter the configuration adapter
	 * @return Return true if outdated
	 */
	public boolean checkVersion(@NonNull ConfigurationAdapter confAdapter) {
		return confAdapter.getInt("dont-edit-this.version", -1) < getLatestVersion();
	}
	
	/**
	 * Load default values
	 */
	public abstract void loadDefaults();
	
	/**
	 * Load configuration from given configuration adapter
	 *
	 * @param confAdapter the configuration adapter
	 */
	public abstract void loadConfiguration(ConfigurationAdapter confAdapter);
	
	/**
	 * Get the configuration file name
	 *
	 * @return the configuration file name
	 */
	public abstract String getFileName();
	
	/**
	 * Get the configuration file path
	 *
	 * @return the configuration resource name
	 */
	public abstract String getResourceName();
	
	/**
	 * Get latest version of the configuration
	 *
	 * @return the latest configuration version
	 */
	public abstract int getLatestVersion();
}
