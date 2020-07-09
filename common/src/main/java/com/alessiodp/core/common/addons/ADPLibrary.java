package com.alessiodp.core.common.addons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ADPLibrary {
	// YAML
	CONFIGURATE_CORE("configurate-core"),
	CONFIGURATE_YAML("configurate-yaml"),
	
	// SQL
	REFLECTIONS("reflections"),
	JDBI("jdbi3-core"),
	JDBI_SQLOBJECT("jdbi3-sqlobject"),
	SLF4J_API("slf4j-api"),
	SLF4J_SIMPLE("slf4j-simple"),
	ANTLR4("antlr4-runtime"),
	CAFFEINE("caffeine"),
	GEANTYREF("geantyref"),
	
	// MySQL
	HIKARICP("hikaricp"),
	
	// SQLite
	SQLITE_JDBC("sqlite-jdbc"),
	
	// H2
	H2("h2");
	
	private final String id;
}
