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
	JDBI_STRINGTEMPLATE4("jdbi3-stringtemplate4"),
	JDBI_STRINGTEMPLATE4_ANTLR("ST4"),
	JDBI_SQLOBJECT("jdbi3-sqlobject"),
	SLF4J_API("slf4j-api"),
	SLF4J_SIMPLE("slf4j-simple"),
	ANTLR("antlr-runtime"),
	ANTLR4("antlr4-runtime"),
	CAFFEINE("caffeine"),
	GEANTYREF("geantyref"),
	
	// Remote SQL
	HIKARICP("hikaricp"),
	
	// MariaDB
	MARIADB("mariadb-java-client"),
	
	// MySQL
	MYSQL("mysql-connector-java"),
	
	// PostgreSQL
	POSTGRESQL("postgresql"),
	
	// SQLite
	SQLITE_JDBC("sqlite-jdbc"),
	
	// H2
	H2("h2"),
	
	// Scripting
	NASHORN("nashorn"),
	OW2_ASM("asm");
	
	private final String id;
}
