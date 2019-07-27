package com.alessiodp.core.common.addons.libraries;

import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;

public enum ILibrary {
	// Library to handle yaml database
	CONFIGURATE_CORE("configurate-core",
			"%CONFIGURATE%",
			"org.spongepowered",
			"https://repo.spongepowered.org/maven/"),
	CONFIGURATE_YAML("configurate-yaml",
			"%CONFIGURATE%",
			"org.spongepowered",
			"https://repo.spongepowered.org/maven/"),
	// Library to handle SQL databases
	HIKARI("HikariCP",
			"%HIKARICP%",
			"com.zaxxer",
			"https://repo1.maven.org/maven2/"),
	// Driver for MySQL
	SLF4J_API("slf4j-api",
			"%SLF4J%",
			"org.slf4j",
			"https://repo1.maven.org/maven2/"),
	SLF4J_SIMPLE("slf4j-simple",
			"%SLF4J%",
			"org.slf4j",
			"https://repo1.maven.org/maven2/"),
	SQLITE_JDBC("sqlite-jdbc",
			"%SQLITEJDBC%",
			"org.xerial",
			"https://repo1.maven.org/maven2/");
	
	@Getter private final String name;
	@Getter private final String version;
	private final String file;
	private final String pack;
	private final String downloadUrl;
	
	ILibrary(String name, String version, String pack, String downloadUrl) {
		this.name = name;
		this.version = version;
		this.file = "%name%-%version%.jar";
		this.pack = pack;
		this.downloadUrl = downloadUrl;
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
		return downloadUrl + Constants.LIBRARY_MAVEN
				.replace("%package%", pack.replace(".", "/"))
				.replace("%file%", file) // Replace file first to replace next version & name
				.replace("%version%", version)
				.replace("%name%", name);
	}
}
