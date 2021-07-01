package com.alessiodp.core.common.storage.file;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class YAMLDao {
	private final ADPPlugin plugin;
	private final String fileName;
	private final int version;
	
	private Path dataPath;
	private YamlFile dataFile;
	@Getter private boolean failed;
	
	public void initFile() {
		failed = true;
		try {
			initData();
			failed = false;
		} catch (IOException | InvalidConfigurationException ex) {
			plugin.getLoggerManager().printError(String.format(Constants.DEBUG_DB_FILE_CREATEFAIL, ex.getMessage()));
			ex.printStackTrace();
		}
	}
	
	private void initData() throws IOException, InvalidConfigurationException {
		dataPath = createDataFile();
		dataFile = new YamlFile(dataPath.toFile());
		dataFile.load();
		
	}
	
	public void saveFile() throws IOException {
		dataFile.save();
	}
	
	public Path createDataFile() {
		Path ret = plugin.getFolder().resolve(fileName);
		if (!Files.exists(ret)) {
			// Create data file
			try {
				YamlFile dataFile = new YamlFile(ret.toFile());
				dataFile.createOrLoad();
				dataFile.set("database-version", version);
				dataFile.save();
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(String.format(Constants.DEBUG_DB_FILE_CREATEFAIL, ex.getMessage()));
			}
		}
		return ret;
	}
	
	public YamlFile getYaml() {
		return dataFile;
	}
}