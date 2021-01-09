package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public abstract class ConfigurationFile {
	@NonNull protected final ADPPlugin plugin;
	@Getter @Setter private boolean outdated = false;
	@Getter protected YamlFile configuration;
	protected Path configurationPath;
	
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
			if (!Files.exists(ret) && plugin.getResource(getResourceName()) != null) {
				byte[] data = ByteStreams.toByteArray(plugin.getResource(getResourceName()));
				
				Files.write(ret, data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			plugin.getLoggerManager().printErrorStacktrace(Constants.DEBUG_CONFIG_SAVE_ERROR, ex);
		}
		return ret;
	}
	
	/**
	 * Check if configuration file exists
	 *
	 * @return True if exists
	 */
	public boolean exists() {
		return Files.exists(configurationPath);
	}
	
	/**
	 * Check the version of the configuration
	 *
	 * @return Return true if outdated
	 */
	public boolean checkVersion() {
		return configuration.getInt("dont-edit-this.version", -1) < getLatestVersion();
	}
	
	/**
	 * Load default values
	 */
	public abstract void loadDefaults();
	
	/**
	 * Initialize the configuration
	 *
	 * @param folder The plugin folder path
	 */
	public void initializeConfiguration(Path folder) {
		configurationPath = saveDefaultConfiguration(folder);
		configuration = new YamlFile(configurationPath.toFile());
		try {
			configuration.loadWithComments();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Load configuration from given configuration adapter
	 */
	public abstract void loadConfiguration();
	
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
	
	/**
	 * Load default config options
	 */
	public void loadDefaultConfigOptions() {
		YamlFile yf = YamlFile.loadConfiguration(new InputStreamReader(plugin.getResource(getResourceName())));
		loadFromConfiguration(yf);
	}
	
	/**
	 * Load config options
	 */
	public void loadConfigOptions() {
		YamlFile yf = new YamlFile(configurationPath.toFile());
		try {
			yf.loadWithComments();
			
			loadFromConfiguration(yf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadFromConfiguration(YamlFile yf) {
		Field[] fs = getClass().getFields();
		for (Field f : fs) {
			ConfigOption co = f.getAnnotation(ConfigOption.class);
			if (co != null) {
				try {
					Object value = null;
					
					// If are lists, better use direct get
					if (f.getType() == List.class && f.getGenericType() instanceof ParameterizedType) {
						Type type = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
						if (type == Integer.class) {
							value = yf.getIntegerList(co.path());
						} else if (type == Double.class) {
							value = yf.getDoubleList(co.path());
						} else if (type == Float.class) {
							value = yf.getFloatList(co.path());
						} else if (type == Short.class) {
							value = yf.getShortList(co.path());
						}
					}
					
					// Otherwise get it normally
					if (value == null)
						value = yf.get(co.path());
					
					if (value != null) {
						/*
						if (value instanceof List && f.getGenericType() instanceof ParameterizedType) {
							// If its a list, filter for its type
							Type type = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
							if (type == Double.class) {
								f.set(null, yf.getDoubleList(co.path()));
							}
							/*
							Type type = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
							if (type == Integer.class) {
								ArrayList<Integer> newValue = new ArrayList<>();
								((List<?>) value).forEach(v -> {
									newValue.add((Integer) v);
								});
								f.set(null, newValue);
							} else if (type == Double.class) {
								ArrayList<Double> newValue = new ArrayList<>();
								((List<?>) value).forEach(v -> {
									newValue.add((Double) v);
								});
								f.set(null, newValue);
							} else
								f.set(null, value);*/
						/*} else*/
						f.set(null, value);
					} else if (!co.nullable()) {
						plugin.getLoggerManager().printError(String.format(Constants.DEBUG_CONFIG_NOTFOUND, co.path(), getFileName()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Save configuration
	 */
	public void save() {
		try {
			Field[] fs = getClass().getFields();
			for (Field f : fs) {
				ConfigOption co = f.getAnnotation(ConfigOption.class);
				if (co != null) {
					configuration.set(co.path(), f.get(null));
				}
			}
			
			configuration.save();
			this.loadConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
