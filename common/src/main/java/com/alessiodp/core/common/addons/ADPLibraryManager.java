package com.alessiodp.core.common.addons;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;
import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.logging.LogLevel;

/*
 * SHA-256 Checksum HEX => Base64
 */
public class ADPLibraryManager {
	private final ADPPlugin plugin;
	private final LibraryManager libraryManager;
	
	public ADPLibraryManager(@NonNull ADPPlugin plugin, @NonNull LibraryManager libraryManager) {
		this.plugin = plugin;
		this.libraryManager = libraryManager;
		libraryManager.setLogLevel(LogLevel.DEBUG);
		
		libraryManager.addMavenCentral();
		libraryManager.addRepository("https://repo.spongepowered.org/maven/");
	}
	
	public ClassLoader getIsolatedClassLoaderOf(ADPLibrary library) {
		return libraryManager.getIsolatedClassLoaderOf(library.getId());
	}
	
	public void setupLibrariesForYAML() {
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.CONFIGURATE_YAML.getId())
				.groupId("org{}spongepowered")
				.artifactId("configurate-yaml")
				.version(LibraryVersions.CONFIGURATE)
				.checksum("OBfYn4nSMGZfVf2DoZhZq+G9TF1mODX/C5OOz/mkPmc=")
				.relocate("ninja{}leaping{}configurate", plugin.getPackageName() + ".libs.configurate")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.CONFIGURATE_CORE.getId())
				.groupId("org{}spongepowered")
				.artifactId("configurate-core")
				.version(LibraryVersions.CONFIGURATE)
				.checksum("XF2LzWLkSV0wyQRDt33I+gDlf3t2WzxH1h8JCZZgPp4=")
				.relocate("ninja{}leaping{}configurate", plugin.getPackageName() + ".libs.configurate")
				.build());
	}
	
	public void setupLibrariesForMySQL() {
		setupLibrariesForSQL();
		setupLibrariesForRemoteSQL();
		
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.MYSQL.getId())
				.groupId("mysql")
				.artifactId("mysql-connector-java")
				.version(LibraryVersions.MYSQL)
				.checksum("oKG+A4nlQdrYhBsybnnFHTmrvhylImcwTXbRz0gBzpY=")
				.relocate("com{}mysql", plugin.getPackageName() + ".libs.mysql")
				.build());
	}
	
	public void setupLibrariesForMariaDB() {
		setupLibrariesForSQL();
		setupLibrariesForRemoteSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.MARIADB.getId())
				.groupId("org{}mariadb{}jdbc")
				.artifactId("mariadb-java-client")
				.version(LibraryVersions.MARIADB)
				.checksum("o/Z3bfCELPZefxWFFQEtUwfalJ9mBCKC4e5EdN0Z9Eg=")
				.relocate("org{}mariadb", plugin.getPackageName() + ".libs.mariadb")
				.build());
	}
	
	public void setupLibrariesForPostgreSQL() {
		setupLibrariesForSQL();
		setupLibrariesForRemoteSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.POSTGRESQL.getId())
				.groupId("org{}postgresql")
				.artifactId("postgresql")
				.version(LibraryVersions.POSTGRESQL)
				.checksum("cIspOK6oHzK4s1RA2iM4KChKEA5vIhKnqx7q9LOmUU4=")
				.relocate("org{}postgresql", plugin.getPackageName() + ".libs.postgresql")
				.build());
	}
	
	private void setupLibrariesForRemoteSQL() {
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.HIKARICP.getId())
				.groupId("com{}zaxxer")
				.artifactId("HikariCP")
				.version(LibraryVersions.HIKARICP)
				.checksum("fAJK7/HBBjV210RTUT+d5kR9jmJNF/jifzCi6XaIxsk=")
				.relocate("com{}zaxxer{}hikari", plugin.getPackageName() + ".libs.hikari")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
	}
	
	public void setupLibrariesForSQLite() {
		setupLibrariesForSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.SQLITE_JDBC.getId())
				.groupId("org{}xerial")
				.artifactId("sqlite-jdbc")
				.version(LibraryVersions.SQLITE_JDBC)
				.checksum("YFl5yU5/4AQ38eENz6ZXoj8SXI600vDsF+P4RhOJTMM=")
				.isolatedLoad(true)
				// Unnecessary relocation
				.build());
	}
	
	public void setupLibrariesForH2() {
		setupLibrariesForSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.H2.getId())
				.groupId("com{}h2database")
				.artifactId("h2")
				.version(LibraryVersions.H2)
				.checksum("OtmsS2qunNnTrBxEdGXh7QYBm4UbiT3WqNdt222FvKY=")
				.isolatedLoad(true)
				// Unnecessary relocation
				.build());
	}
	
	private void setupLibrariesForSQL() {
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.REFLECTIONS.getId())
				.groupId("org{}reflections")
				.artifactId("reflections")
				.version(LibraryVersions.REFLECTIONS)
				.checksum("0Wj1jTLyrnrFqNXZCSre7lJsYEtBEl3LRe6od5YKmc8=")
				.relocate("org{}reflections", plugin.getPackageName() + ".libs.reflections")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI.getId())
				.groupId("org{}jdbi")
				.artifactId("jdbi3-core")
				.version(LibraryVersions.JDBI)
				.checksum("GG1GYDFTDZyHDtq8zS+6DzBbW0qYsNL24i7r7FTBJT0=")
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI_STRINGTEMPLATE4.getId())
				.groupId("org{}jdbi")
				.artifactId("jdbi3-stringtemplate4")
				.version(LibraryVersions.JDBI)
				.checksum("5eohoK7IBj9z7Z2pJsvyAJSvWMaCx5jJrUekaC4vwX4=")
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}stringtemplate{}v4", plugin.getPackageName() + ".libs.stringtemplate.v4")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI_STRINGTEMPLATE4_ANTLR.getId())
				.groupId("org{}antlr")
				.artifactId("ST4")
				.version(LibraryVersions.JDBI_ST4)
				.checksum("68nZvNtnVxwINf9EHq1cHekKJaDT+oQGVKFE6PoEENQ=")
				.relocate("org{}stringtemplate{}v4", plugin.getPackageName() + ".libs.stringtemplate.v4")
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI_SQLOBJECT.getId())
				.groupId("org{}jdbi")
				.artifactId("jdbi3-sqlobject")
				.version(LibraryVersions.JDBI)
				.checksum("hj1rJnxknEcFdyIHwsSgTk5+NGHQuXI1I/9xMvIl/58=")
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.SLF4J_API.getId())
				.groupId("org{}slf4j")
				.artifactId("slf4j-api")
				.version(LibraryVersions.SLF4J)
				.checksum("zboHlk0btAoHYUhcax6ML4/Z6x0ZxTkorA1/lRAQXFc=")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.SLF4J_SIMPLE.getId())
				.groupId("org{}slf4j")
				.artifactId("slf4j-simple")
				.version(LibraryVersions.SLF4J)
				.checksum("i5J5y/9rn4hZTvrjzwIDm2mVAw7sAj7UOSh0jEFnD+4=")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.ANTLR.getId())
				.groupId("org.antlr")
				.artifactId("antlr-runtime")
				.version(LibraryVersions.JDBI_ANTLR)
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.checksum("zj/I7LEPOemjzdy7LONQ0nLZzT0LHhjm/nPDuTichzQ=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.ANTLR4.getId())
				.groupId("org.antlr")
				.artifactId("antlr4-runtime")
				.version(LibraryVersions.JDBI_ANTLR4)
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.checksum("TFGLh9S9/4tEzYy8GvgW6US2Kj/luAt4FQHPH0dZu8Q=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.CAFFEINE.getId())
				.groupId("com.github.ben-manes.caffeine")
				.artifactId("caffeine")
				.version(LibraryVersions.JDBI_CAFFEINE)
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.checksum("sRB6QJe+RRWpI6Vbxj2gTkEeaWSqBFvs4bx6y4SHLtc=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.GEANTYREF.getId())
				.groupId("io.leangen.geantyref")
				.artifactId("geantyref")
				.version(LibraryVersions.JDBI_GEANTYREF)
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.checksum("+JH4yXPM0d/LhBv+2EZOiWsbMN3dA9kzxL6p5CihEaI=")
				.build());
	}
	
	public void setupLibrariesForScripting() {
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.NASHORN.getId())
				.groupId("org{}openjdk{}nashorn")
				.artifactId("nashorn-core")
				.version(LibraryVersions.NASHORN)
				.relocate("org{}openjdk{}nashorn", plugin.getPackageName() + ".libs.nashorn")
				.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
				.checksum("3XzmRuCVWX5Rqbmg0olGM+SxAZOwDdUU/bXB9Ckp/D8=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.OW2_ASM.getId())
				.groupId("org{}ow2{}asm")
				.artifactId("asm")
				.version(LibraryVersions.OW2_ASM)
				.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
				.checksum("L2fhHO7IGevYjd7lMAq6aZscurLiDCLpfPAn076TlZs=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.OW2_ASM.getId())
				.groupId("org{}ow2{}asm")
				.artifactId("asm-util")
				.version(LibraryVersions.OW2_ASM)
				.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
				.checksum("GCEoWSdC7UiDrIK/IF8Te2v74SNMaOb+sTdZ51qFtyk=")
				.build());
	}
}
