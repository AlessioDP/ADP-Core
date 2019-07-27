package com.alessiodp.core.common.addons.libraries;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.google.common.io.ByteStreams;
import lombok.NonNull;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibraryManager {
	@NonNull private final ADPPlugin plugin;
	private final Path dataFolder;
	
	public LibraryManager(ADPPlugin instance) {
		plugin = instance;
		dataFolder = plugin.getFolder().resolve(Constants.LIBRARY_FOLDER);
	}
	
	/**
	 * Initialize given library
	 *
	 * @param library the library to initialize
	 * @return true if the library has been initialized
	 */
	public boolean initLibrary(@NonNull ILibrary library) {
		boolean ret = true;
		plugin.getLoggerManager().logDebug(Constants.DEBUG_LIB_INIT_INIT
				.replace("{lib}", library.getName())
				.replace("{version}", library.getVersion()), true);
		if (!existLibrary(library)) {
			plugin.getLoggerManager().logDebug(Constants.DEBUG_LIB_INIT_DL
					.replace("{lib}", library.getName())
					.replace("{version}", library.getVersion()), true);
			ret = downloadLibrary(library);
		}
		if (ret) {
			plugin.getLoggerManager().logDebug(Constants.DEBUG_LIB_INIT_LOAD
					.replace("{lib}", library.getName())
					.replace("{version}", library.getVersion()), true);
			ret = loadLibrary(library);
		}
		
		if (!ret) {
			plugin.getLoggerManager().logDebug(Constants.DEBUG_LIB_INIT_FAIL
					.replace("{lib}", library.getName())
					.replace("{version}", library.getVersion()), true);
		}
		return ret;
	}
	
	private boolean existLibrary(@NonNull ILibrary library) {
		return Files.exists(dataFolder.resolve(library.getFile()));
	}
	
	private boolean downloadLibrary(@NonNull ILibrary library) {
		boolean ret = false;
		try {
			Path filePath = dataFolder.resolve(library.getFile());
			
			if (!Files.exists(filePath)) {
				URL url = new URL(library.getDownloadUrl());
				
				Files.createDirectories(dataFolder);
				
				try (InputStream input = url.openStream()) {
					byte[] data = ByteStreams.toByteArray(input);
					
					Files.write(filePath, data);
				}
			}
			ret = true;
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_LIB_FAILED_DL
					.replace("{lib}", library.getName())
					.replace("{version}", library.getVersion())
					.replace("{message}", ex.getMessage()));
		}
		return ret;
	}
	
	private boolean loadLibrary(@NonNull ILibrary library) {
		boolean ret = false;
		Path filePath = dataFolder.resolve(library.getFile());
		if (Files.exists(filePath)) {
			try {
				Class<URLClassLoader> sysclass = URLClassLoader.class;
				Method m = sysclass.getDeclaredMethod("addURL", URL.class);
				m.setAccessible(true);
				
				m.invoke(plugin.getClass().getClassLoader(), filePath.toUri().toURL());
				ret = true;
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(Constants.DEBUG_LIB_FAILED_LOAD
						.replace("{lib}", library.getName())
						.replace("{version}", library.getVersion()));
				ex.printStackTrace();
			}
		}
		return ret;
	}
}
