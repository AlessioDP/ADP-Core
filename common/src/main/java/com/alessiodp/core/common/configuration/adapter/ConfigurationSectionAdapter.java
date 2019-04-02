package com.alessiodp.core.common.configuration.adapter;

import java.util.Set;

public interface ConfigurationSectionAdapter extends ConfigurationAdapter {
	/**
	 * Get all keys of the configuration section
	 *
	 * @return a list of available keys
	 */
	Set<String> getKeys();
}