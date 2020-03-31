package com.alessiodp.core.common.addons;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;
import net.byteflux.libby.Library;
import net.byteflux.libby.LibraryManager;
import net.byteflux.libby.logging.LogLevel;

/*
 * SHA-256 Checksum => Base64 HEX
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
				.version("%CONFIGURATE%")
				.checksum("A14cN5/4eqyOzBOfDs3z3noUiBG7p9joKVvBihs+FW0=")
				.relocate("ninja{}leaping{}configurate{}yaml", plugin.getPackageName() + ".libs.configurate.yaml")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}spongepowered")
				.artifactId("configurate-core")
				.version("%CONFIGURATE%")
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
				.version("%HIKARICP%")
				.checksum("rnp2e/N8l5JSPtPtcitG6M8jYPVG9iUOuYyDNVrWl/k=")
				.relocate("com{}zaxxer{}hikari", plugin.getPackageName() + ".libs.hikari")
				.build());
	}
	
	public void setupLibrariesForSQLite() {
		setupLibrariesForSQL();
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}xerial")
				.artifactId("sqlite-jdbc")
				.version("%SQLITEJDBC%")
				.checksum("KAA0qJkwABBMWza8XhE5sOgt8d6c/ZUfUpva3q9vRW0=")
				// Unnecessary relocation
				.build());
	}
	
	private void setupLibrariesForSQL() {
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}reflections")
				.artifactId("reflections")
				.version("%REFLECTIONS%")
				.checksum("0Wj1jTLyrnrFqNXZCSre7lJsYEtBEl3LRe6od5YKmc8=")
				.relocate("org{}reflections", plugin.getPackageName() + ".libs.reflections")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}slf4j")
				.artifactId("slf4j-api")
				.version("%SLF4J%")
				.checksum("GMSgCV1cHaa4F1kudnuyPSndL1YK1033X/OWHb3iW3k=")
				.relocate("org{}slf4j", plugin.getPackageName() + ".libs.slf4j.api")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}slf4j")
				.artifactId("slf4j-simple")
				.version("%SLF4J%")
				.checksum("CWbob/+lvlLT2ee4ndZ02YoD7tCkVPuvfBvZSTvZ2HQ=")
				.relocate("org{}slf4j{}simple", plugin.getPackageName() + ".libs.slf4j.simple")
				.build());
		
		System.getProperties().setProperty("org.jooq.no-logo", "TRUE");
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}jooq")
				.artifactId("jooq")
				.version("%JOOQ%")
				.checksum("h+7+KZF59m12CJdxg9vKAxtLhqPLf41glvZbB6jo67Q=")
				.relocate("org{}jooq", plugin.getPackageName() + ".libs.jooq")
				.build());
		
		libraryManager.loadLibrary(Library.builder()
				.groupId("org{}reactivestreams")
				.artifactId("reactive-streams")
				.version("%JOOQ_REACTIVE%")
				.checksum("zAmrCxQODQSWwhZdSzLOJPTWRGwKJsXcd7Br35nuj64=")
				.build());
	}
}