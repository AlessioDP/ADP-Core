package com.alessiodp.core.common.addons.libraries;

import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;

public enum ILibrary {
	// Library to handle yaml database
	CONFIGURATE_CORE("configurate-core",
			"%CONFIGURATE%",
			"ninja.leaping.configurate"),
	CONFIGURATE_YAML("configurate-yaml",
			"%CONFIGURATE%",
			"ninja.leaping.configurate"),
	// Library to handle SQL databases
	HIKARI("HikariCP",
			"%HIKARICP%",
			"com.zaxxer"),
	// Driver for MySQL
	SLF4J_API("slf4j-api",
			"%SLF4J%",
			"org.slf4j"),
	SLF4J_SIMPLE("slf4j-simple",
			"%SLF4J%",
			"org.slf4j"),
	SQLITE_JDBC("sqlite-jdbc",
			"%SQLITEJDBC%",
			"org.xerial");
	
	@Getter private final String name;
	@Getter private final String version;
	private final String file;
	private final String pack;
	
	ILibrary(String name, String version, String pack) {
		this.name = name;
		this.version = version;
		this.file = "%name%-%version%.jar";
		this.pack = pack;
	}
	
	/**
	 * Get the file name
	 *
	 * @return the file name
	 */
	public String getFile() {
		return file
				.replace("%name%", name)
				.replace("%version%", version);
	}
	
	/**
	 * Get the download url
	 *
	 * @return the url
	 */
	public String getDownloadUrl() {
		return Constants.LIBRARY_URL
				.replace("%package%", pack.replace(".", "/"))
				.replace("%file%", file) // Replace file first to replace next version & name
				.replace("%version%", version)
				.replace("%name%", name);
	}
}
