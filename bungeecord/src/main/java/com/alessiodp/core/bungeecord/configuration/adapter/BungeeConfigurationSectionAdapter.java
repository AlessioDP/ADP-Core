package com.alessiodp.core.bungeecord.configuration.adapter;

import com.alessiodp.core.common.configuration.adapter.ConfigurationSectionAdapter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.config.Configuration;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BungeeConfigurationSectionAdapter implements ConfigurationSectionAdapter {
	private final Configuration configurationSection;
	
	@Override
	public void reload() {
		// Reload not required
	}
	
	@Override
	public boolean contains(String path) {
		return configurationSection.contains(path);
	}
	
	@Override
	public ConfigurationSectionAdapter getConfigurationSection(String path) {
		Configuration conf = configurationSection.getSection(path);
		return conf != null ? new BungeeConfigurationSectionAdapter(conf) : null;
	}
	
	@Override
	public boolean getBoolean(String path, boolean def) {
		return configurationSection.getBoolean(path, def);
	}
	
	@Override
	public double getDouble(String path, double def) {
		return configurationSection.getDouble(path, def);
	}
	
	@Override
	public int getInt(String path, int def) {
		return configurationSection.getInt(path, def);
	}
	
	@Override
	public String getString(String path, String def) {
		return configurationSection.getString(path, def);
	}
	
	@Override
	public List<String> getStringList(String path, List<String> def) {
		List<String> ret = def;
		if (contains(path)) {
			ret = configurationSection.getStringList(path);
		}
		return ret;
	}
	
	@Override
	public Set<String> getKeys() {
		return (Set<String>) configurationSection.getKeys();
	}
}
