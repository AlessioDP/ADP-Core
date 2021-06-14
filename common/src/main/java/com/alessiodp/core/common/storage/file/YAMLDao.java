package com.alessiodp.core.common.storage.file;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.interfaces.IDatabaseFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class YAMLDao implements IDatabaseFile {
	private final ADPPlugin plugin;
	private final String fileName;
	private final int version;
	
	private Path dataPath;
	private YamlConfigurationLoader dataLoader;
	private ConfigurationNode dataNode;
	@Getter private boolean failed;
	
	@Override
	public void initFile() {
		failed = false;
		try {
			initData();
		} catch (IOException ex) {
			plugin.getLoggerManager().printError(String.format(Constants.DEBUG_DB_FILE_CREATEFAIL, "YAML", ex.getMessage()));
			failed = true;
		}
		
		dataPath = createDataFile();
	}
	
	private void initData() throws IOException {
		dataPath = createDataFile();
		dataLoader = YamlConfigurationLoader
				.builder()
				.path(dataPath)
				.nodeStyle(NodeStyle.BLOCK)
				.indent(2)
				.build();
		dataNode = dataLoader.load();
	}
	
	@Override
	public void saveFile() throws IOException {
		dataLoader.save(dataNode);
	}
	
	@Override
	public Path createDataFile() {
		Path ret = plugin.getFolder().resolve(fileName);
		if (!Files.exists(ret)) {
			// Create data file
			try {
				YamlConfigurationLoader loader = YamlConfigurationLoader
						.builder()
						.path(ret)
						.nodeStyle(NodeStyle.BLOCK)
						.indent(2)
						.build();
				ConfigurationNode node = loader.load();
				node.node("database-version").set(version);
				loader.save(node);
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(String.format(Constants.DEBUG_DB_FILE_CREATEFAIL, "YAML", ex.getMessage()));
			}
		}
		return ret;
	}
	
	@Override
	public ConfigurationNode getRootNode() {
		return dataNode;
	}
}