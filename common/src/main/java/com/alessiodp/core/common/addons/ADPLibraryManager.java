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
	
	public void setupLibrariesForYAML() {
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}spongepowered")
				.artifactId("configurate-yaml")
				.version(LibraryVersions.CONFIGURATE)
				.checksum("A14cN5/4eqyOzBOfDs3z3noUiBG7p9joKVvBihs+FW0=")
				.relocate("ninja{}leaping{}configurate{}yaml", plugin.getPackageName() + ".libs.configurate.yaml")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}spongepowered")
				.artifactId("configurate-core")
				.version(LibraryVersions.CONFIGURATE)
				.checksum("O889bmLMJOGo2Klj4u+KyY6IarwQstZYRt3T0g09SXs=")
				.relocate("ninja{}leaping{}configurate", plugin.getPackageName() + ".libs.configurate")
				.build());
	}
	
	public void setupLibrariesForMySQL() {
		setupLibrariesForSQL();
		setupLibrariesForYAML();
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("com{}zaxxer")
				.artifactId("HikariCP")
				.version(LibraryVersions.HIKARICP)
				.checksum("i3MvlHBXDUqEHcHvbIJrWGl4sluoMHEv8fpZ3idd+mE=")
				.relocate("com{}zaxxer{}hikari", plugin.getPackageName() + ".libs.hikari")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
	}
	
	public void setupLibrariesForSQLite() {
		setupLibrariesForSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}xerial")
				.artifactId("sqlite-jdbc")
				.version(LibraryVersions.SQLITE_JDBC)
				.checksum("xRv+M+D7Dcqs/CPs4+DuGoiAqx0mF2dpBMaUv8a3xcs=")
				// Unnecessary relocation
				.build());
	}
	
	public void setupLibrariesForH2() {
		setupLibrariesForSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("com{}h2database")
				.artifactId("h2")
				.version(LibraryVersions.H2)
				.checksum("OtmsS2qunNnTrBxEdGXh7QYBm4UbiT3WqNdt222FvKY=")
				// Unnecessary relocation
				.build());
	}
	
	private void setupLibrariesForSQL() {
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}reflections")
				.artifactId("reflections")
				.version(LibraryVersions.REFLECTIONS)
				.checksum("0Wj1jTLyrnrFqNXZCSre7lJsYEtBEl3LRe6od5YKmc8=")
				.relocate("org{}reflections", plugin.getPackageName() + ".libs.reflections")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}jdbi")
				.artifactId("jdbi3-core")
				.version(LibraryVersions.JDBI)
				.checksum("NgBs7V5CBBULkTo5SnZMoJCcF1kwwQpQUdii6+a6cQU=")
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}jdbi")
				.artifactId("jdbi3-sqlobject")
				.version(LibraryVersions.JDBI)
				.checksum("1sSOoe8hHc7cfLcjsKsEss+okXRJ+kPkZ4qmqafjRHw=")
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}slf4j")
				.artifactId("slf4j-api")
				.version(LibraryVersions.SLF4J)
				.checksum("zboHlk0btAoHYUhcax6ML4/Z6x0ZxTkorA1/lRAQXFc=")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}slf4j")
				.artifactId("slf4j-simple")
				.version(LibraryVersions.SLF4J)
				.checksum("i5J5y/9rn4hZTvrjzwIDm2mVAw7sAj7UOSh0jEFnD+4=")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org.antlr")
				.artifactId("antlr4-runtime")
				.version(LibraryVersions.JDBI_ANTLR)
				.relocate("org{}jdbi", plugin.getPackageName() + ".libs.jdbi")
				.checksum("TFGLh9S9/4tEzYy8GvgW6US2Kj/luAt4FQHPH0dZu8Q=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("com.github.ben-manes.caffeine")
				.artifactId("caffeine")
				.version(LibraryVersions.JDBI_CAFFEINE)
				.relocate("com{}github{}benmanes{}caffeine", plugin.getPackageName() + ".libs.caffeine")
				.checksum("sRB6QJe+RRWpI6Vbxj2gTkEeaWSqBFvs4bx6y4SHLtc=")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("io.leangen.geantyref")
				.artifactId("geantyref")
				.version(LibraryVersions.JDBI_GEANTYREF)
				.relocate("io{}leangen{}geantyref", plugin.getPackageName() + ".libs.geantyref")
				.checksum("+JH4yXPM0d/LhBv+2EZOiWsbMN3dA9kzxL6p5CihEaI=")
				.build());
	}
}
