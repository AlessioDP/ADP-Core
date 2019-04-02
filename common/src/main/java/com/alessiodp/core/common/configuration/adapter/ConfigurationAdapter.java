package com.alessiodp.core.common.configuration.adapter;

import java.util.List;

public interface ConfigurationAdapter {
	/**
	 * Reload the configuration
	 */
	void reload();
	
	/**
	 * Does the configuration contains the path
	 *
	 * @param path the configuration path
	 * @return true if the configuration contains the given path
	 */
	boolean contains(String path);
	
	/**
	 * Get a section of the configuration
	 *
	 * @param path the configuration path
	 * @return the configuration section of the given path
	 */
	ConfigurationSectionAdapter getConfigurationSection(String path);
	
	/**
	 * Get a boolean and if it doesn't exist returns def
	 *
	 * @param path the configuration path
	 * @param def the default value
	 * @return the found boolean value
	 */
	boolean getBoolean(String path, boolean def);
	
	/**
	 * Get a double and if it doesn't exist returns def
	 *
	 * @param path the configuration path
	 * @param def the default value
	 * @return the found double value
	 */
	double getDouble(String path, double def);
	
	/**
	 * Get an integer and if it doesn't exist returns def
	 *
	 * @param path the configuration path
	 * @param def the default value
	 * @return the found integer value
	 */
	int getInt(String path, int def);
	
	/**
	 * Get a string and if it doesn't exist returns def
	 *
	 * @param path the configuration path
	 * @param def the default value
	 * @return the found string value
	 */
	String getString(String path, String def);
	
	/**
	 * Get a string list and if it doesn't exist returns def
	 *
	 * @param path the configuration path
	 * @param def the default value
	 * @return the found string list value
	 */
	List<String> getStringList(String path, List<String> def);
}