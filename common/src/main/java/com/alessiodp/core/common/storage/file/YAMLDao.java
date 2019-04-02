package com.alessiodp.core.common.storage.file;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.libraries.ILibrary;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.interfaces.IDatabaseFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class YAMLDao implements IDatabaseFile {
	private final ADPPlugin plugin;
	private final String fileName;
	private final int version;
	
	private Path dataPath;
	private ConfigurationLoader dataLoader;
	private ConfigurationNode dataNode;
	@Getter private boolean failed;
	
	@Override
	public void initFile() {
		failed = false;
		if (plugin.getLibraryManager().initLibrary(ILibrary.CONFIGURATE_YAML)) {
			try {
				initData();
			} catch (IOException ex) {
				plugin.getLoggerManager().printError(Constants.DEBUG_DB_FILE_CREATEFAIL
						.replace("{message}", ex.getMessage()));
				failed = true;
			}
		} else {
			failed = true;
		}
		
		dataPath = createDataFile();
	}
	
	private void initData() throws IOException {
		dataPath = createDataFile();
		dataLoader = YAMLConfigurationLoader
				.builder()
				.setPath(dataPath)
				.setFlowStyle(DumperOptions.FlowStyle.BLOCK)
				.setIndent(2)
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
				ConfigurationLoader<ConfigurationNode> loader = YAMLConfigurationLoader
						.builder()
						.setPath(ret)
						.setFlowStyle(DumperOptions.FlowStyle.BLOCK)
						.setIndent(2)
						.build();
				ConfigurationNode node = loader.load();
				node.getNode("database-version").setValue(version);
				loader.save(node);
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(Constants.DEBUG_DB_FILE_CREATEFAIL
						.replace("{message}", ex.getMessage()));
			}
		}
		return ret;
	}
	
	@Override
	public ConfigurationNode getRootNode() {
		return dataNode;
	}
}