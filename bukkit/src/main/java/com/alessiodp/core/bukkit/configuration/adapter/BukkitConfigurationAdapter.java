package com.alessiodp.core.bukkit.configuration.adapter;

import com.alessiodp.core.common.configuration.adapter.ConfigurationAdapter;
import com.alessiodp.core.common.configuration.adapter.ConfigurationSectionAdapter;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.nio.file.Path;
import java.util.List;

public class BukkitConfigurationAdapter implements ConfigurationAdapter {
	private YamlConfiguration yaml;
	private final Path filePath;
	
	public BukkitConfigurationAdapter(@NonNull Path filePath) {
		this.filePath = filePath;
		reload();
	}
	
	@Override
	public void reload() {
		yaml = YamlConfiguration.loadConfiguration(filePath.toFile());
	}
	
	@Override
	public boolean contains(String path) {
		return yaml.contains(path);
	}
	
	@Override
	public ConfigurationSectionAdapter getConfigurationSection(String path) {
		ConfigurationSection conf = yaml.getConfigurationSection(path);
		return conf != null ? new BukkitConfigurationSectionAdapter(conf) : null;
	}
	
	@Override
	public boolean getBoolean(String path, boolean def) {
		return yaml.getBoolean(path, def);
	}
	
	@Override
	public double getDouble(String path, double def) {
		return yaml.getDouble(path, def);
	}
	
	@Override
	public int getInt(String path, int def) {
		return yaml.getInt(path, def);
	}
	
	@Override
	public String getString(String path, String def) {
		return yaml.getString(path, def);
	}
	
	@Override
	public List<String> getStringList(String path, List<String> def) {
		List<String> ret = def;
		if (contains(path)) {
			ret = yaml.getStringList(path);
		}
		return ret;
	}
}
