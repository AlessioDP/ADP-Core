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
	
	public ADPLibraryManager(@NonNull ADPPlugin plugin, LibraryManager libraryManager) {
		this.plugin = plugin;
		this.libraryManager = libraryManager;
		if (libraryManager != null) {
			// LibraryManager is null for Spigot/Bungee 1.16+
			libraryManager.setLogLevel(LogLevel.DEBUG);
			
			libraryManager.addMavenCentral();
			libraryManager.addRepository("https://repo.spongepowered.org/maven/");
		}
	}
	
	public ClassLoader getIsolatedClassLoaderOf(ADPLibrary library) {
		return libraryManager != null ? libraryManager.getIsolatedClassLoaderOf(library.getId()) : plugin.getClass().getClassLoader();
	}
	
	public void setupLibrariesForYAML() {
		if (!plugin.areLibrariesSupported()) {
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.CONFIGURATE_YAML.getId())
					.groupId("org{}spongepowered")
					.artifactId("configurate-yaml")
					.version(LibraryVersions.CONFIGURATE)
					.relocate("org{}spongepowered{}configurate", plugin.getPackageName() + ".libs.configurate")
					.build());
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.CONFIGURATE_CORE.getId())
					.groupId("org{}spongepowered")
					.artifactId("configurate-core")
					.version(LibraryVersions.CONFIGURATE)
					.relocate("org{}spongepowered{}configurate", plugin.getPackageName() + ".libs.configurate")
					.build());
		}
	}
	
	public void setupLibrariesForMySQL() {
		if (!plugin.areLibrariesSupported()) {
			setupLibrariesForSQL();
			setupLibrariesForRemoteSQL();
			
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.MYSQL.getId())
					.groupId("mysql")
					.artifactId("mysql-connector-java")
					.version(LibraryVersions.MYSQL)
					.relocate("com{}mysql", plugin.getPackageName() + ".libs.mysql")
					.build());
		}
	}
	
	public void setupLibrariesForMariaDB() {
		if (!plugin.areLibrariesSupported()) {
			setupLibrariesForSQL();
			setupLibrariesForRemoteSQL();
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.MARIADB.getId())
					.groupId("org{}mariadb{}jdbc")
					.artifactId("mariadb-java-client")
					.version(LibraryVersions.MARIADB)
					.relocate("org{}mariadb", plugin.getPackageName() + ".libs.mariadb")
					.build());
		}
	}
	
	public void setupLibrariesForPostgreSQL() {
		if (!plugin.areLibrariesSupported()) {
			setupLibrariesForSQL();
			setupLibrariesForRemoteSQL();
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.POSTGRESQL.getId())
					.groupId("org{}postgresql")
					.artifactId("postgresql")
					.version(LibraryVersions.POSTGRESQL)
					.relocate("org{}postgresql", plugin.getPackageName() + ".libs.postgresql")
					.build());
		}
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
		if (!plugin.areLibrariesSupported()) {
			setupLibrariesForSQL();
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.SQLITE_JDBC.getId())
					.groupId("org{}xerial")
					.artifactId("sqlite-jdbc")
					.version(LibraryVersions.SQLITE_JDBC)
					.isolatedLoad(true)
					// Unnecessary relocation
					.build());
		}
	}
	
	public void setupLibrariesForH2() {
		if (!plugin.areLibrariesSupported()) {
			setupLibrariesForSQL();
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.H2.getId())
					.groupId("com{}h2database")
					.artifactId("h2")
					.version(LibraryVersions.H2)
					.isolatedLoad(true)
					// Unnecessary relocation
					.build());
		}
	}
	
	private void setupLibrariesForSQL() {
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI.getId())
				.groupId("org{}jdbi")
				.artifactId("jdbi3-core")
				.version(LibraryVersions.JDBI)
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
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}stringtemplate{}v4", plugin.getPackageName() + ".libs.stringtemplate.v4")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI_STRINGTEMPLATE4_ANTLR.getId())
				.groupId("org{}antlr")
				.artifactId("ST4")
				.version(LibraryVersions.JDBI_ST4)
				.relocate("org{}stringtemplate{}v4", plugin.getPackageName() + ".libs.stringtemplate.v4")
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.JDBI_SQLOBJECT.getId())
				.groupId("org{}jdbi")
				.artifactId("jdbi3-sqlobject")
				.version(LibraryVersions.JDBI)
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
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.SLF4J_SIMPLE.getId())
				.groupId("org{}slf4j")
				.artifactId("slf4j-simple")
				.version(LibraryVersions.SLF4J)
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.ANTLR.getId())
				.groupId("org.antlr")
				.artifactId("antlr-runtime")
				.version(LibraryVersions.JDBI_ANTLR)
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.ANTLR4.getId())
				.groupId("org.antlr")
				.artifactId("antlr4-runtime")
				.version(LibraryVersions.JDBI_ANTLR4)
				.relocate("org{}antlr", plugin.getPackageName() + ".libs.antlr")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.CAFFEINE.getId())
				.groupId("com.github.ben-manes.caffeine")
				.artifactId("caffeine")
				.version(LibraryVersions.JDBI_CAFFEINE)
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.id(ADPLibrary.GEANTYREF.getId())
				.groupId("io.leangen.geantyref")
				.artifactId("geantyref")
				.version(LibraryVersions.JDBI_GEANTYREF)
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.build());
	}
	
	public void setupLibrariesForScripting() {
		if (!plugin.areLibrariesSupported()) {
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.NASHORN.getId())
					.groupId("org{}openjdk{}nashorn")
					.artifactId("nashorn-core")
					.version(LibraryVersions.NASHORN)
					.relocate("org{}openjdk{}nashorn", plugin.getPackageName() + ".libs.nashorn")
					.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
					.build());
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.OW2_ASM.getId())
					.groupId("org{}ow2{}asm")
					.artifactId("asm")
					.version(LibraryVersions.OW2_ASM)
					.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
					.build());
			
			libraryManager.loadLibrary(Library.builder()
					.id(ADPLibrary.OW2_ASM.getId())
					.groupId("org{}ow2{}asm")
					.artifactId("asm-util")
					.version(LibraryVersions.OW2_ASM)
					.relocate("org{}objectweb{}asm", plugin.getPackageName() + ".libs.ow2.asm")
					.build());
		}
	}
}
