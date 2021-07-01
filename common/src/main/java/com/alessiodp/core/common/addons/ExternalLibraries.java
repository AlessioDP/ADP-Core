package com.alessiodp.core.common.addons;

import com.alessiodp.core.common.ADPPlugin;
import io.github.slimjar.relocation.RelocationRule;
import io.github.slimjar.resolver.data.Dependency;
import io.github.slimjar.resolver.data.DependencyData;
import io.github.slimjar.resolver.data.Repository;
import io.github.slimjar.resolver.mirrors.SimpleMirrorSelector;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ExternalLibraries {
	
	public static DependencyData getDependencyData(ADPPlugin plugin) throws IOException {
		Properties versions = new Properties();
		versions.load(plugin.getResource("libraries.properties"));
		
		List<Usage> usages = plugin.getLibrariesUsages();
		ArrayList<Dependency> dependencies = new ArrayList<>();
		ArrayList<RelocationRule> relocations = new ArrayList<>();
		
		if (usages.contains(Usage.H2)) {
			dependencies.add(new Dependency(
					filter("com{}h2database"), "h2", versions.getProperty("h2"), null, Collections.emptyList()
			));
			relocations.add(new RelocationRule(filter("org{}h2"), plugin.getPackageName() + ".libs.h2"));
		}
		
		if (usages.contains(Usage.MYSQL) || usages.contains(Usage.MARIADB) || usages.contains(Usage.POSTGRESQL)) {
			dependencies.add(new Dependency(
					filter("com{}zaxxer"), "HikariCP", versions.getProperty("hikaricp"), null, Collections.emptyList()
			));
			dependencies.add(new Dependency(
					filter("org{}jdbi"), "jdbi3-core", versions.getProperty("jdbi"), null, Arrays.asList(
					new Dependency(
							filter("org{}antlr"), "antlr4-runtime", versions.getProperty("jdbi.antlr4"), null, Collections.emptyList()
					),
					new Dependency(
							filter("io{}leangen{}geantyref"), "geantyref", versions.getProperty("jdbi.geantyref"), null, Collections.emptyList()
					),
					new Dependency(
							filter("com{}github{}ben-manes{}caffeine"), "caffeine", versions.getProperty("jdbi.caffeine"), null, Collections.emptyList()
					)
			)
			));
			dependencies.add(new Dependency(
					filter("org{}jdbi"), "jdbi3-stringtemplate4", versions.getProperty("jdbi"), null, Arrays.asList(
					new Dependency(
							filter("org{}antlr"), "ST4", versions.getProperty("jdbi.st4"), null, Collections.emptyList()
					),
					new Dependency(
							filter("org{}antlr"), "antlr-runtime", versions.getProperty("jdbi.antlr"), null, Collections.emptyList()
					)
			)
			));
			dependencies.add(new Dependency(
					filter("org{}jdbi"), "jdbi3-sqlobject", versions.getProperty("jdbi"), null, Collections.emptyList()
			));
			dependencies.add(new Dependency(
					filter("org{}slf4j"), "slf4j-api", versions.getProperty("slf4j"), null, Collections.emptyList()
			));
			dependencies.add(new Dependency(
					filter("org{}slf4j"), "slf4j-simple", versions.getProperty("slf4j"), null, Collections.emptyList()
			));
			relocations.add(new RelocationRule(filter("com{}zaxxer{}hikari"), plugin.getPackageName() + ".libs.hikari"));
			relocations.add(new RelocationRule(filter("org{}jdbi"), plugin.getPackageName() + ".libs.jdbi"));
			relocations.add(new RelocationRule(filter("org{}slf4j"), plugin.getPackageName() + ".libs.slf4j"));
			relocations.add(new RelocationRule(filter("org{}antlr"), plugin.getPackageName() + ".libs.antlr"));
			relocations.add(new RelocationRule(filter("com{}github{}benmanes{}caffeine"), plugin.getPackageName() + ".libs.caffeine"));
			relocations.add(new RelocationRule(filter("io{}leangen{}geantyref"), plugin.getPackageName() + ".libs.geantyref"));
		}
		
		if (usages.contains(Usage.MARIADB)) {
			dependencies.add(new Dependency(
					filter("org{}mariadb{}jdbc"), "mariadb-java-client", versions.getProperty("mariadb"), null, Collections.emptyList()
			));
			relocations.add(new RelocationRule(filter("org{}mariadb"), plugin.getPackageName() + ".libs.mariadb"));
		}
		
		if (usages.contains(Usage.MYSQL)) {
			dependencies.add(new Dependency(
					filter("mysql"), "mysql-connector-java", versions.getProperty("mysql"), null, Collections.emptyList()
			));
			relocations.add(new RelocationRule(filter("com{}mysql"), plugin.getPackageName() + ".libs.mysql"));
		}
		
		if (usages.contains(Usage.POSTGRESQL)) {
			dependencies.add(new Dependency(
					filter("org{}postgresql"), "postgresql", versions.getProperty("postgresql"), null, Collections.emptyList()
			));
			relocations.add(new RelocationRule(filter("org{}postgresql"), plugin.getPackageName() + ".libs.postgresql"));
		}
		
		if (usages.contains(Usage.SQLITE)) {
			// Load SQLite if somehow its not in the server
			try {
				Class.forName("org.sqlite.SQLiteDataSource");
			} catch (Exception ignored) {
				// Load SQLite
				dependencies.add(new Dependency(
						filter("org{}xerial"), "sqlite-jdbc", versions.getProperty("sqlite"), null, Collections.emptyList()
				));
			}
		}
		
		if (usages.contains(Usage.SCRIPT)) {
			// Load new Nashorn if Java 15+
			if (plugin.getJavaVersion() >= 15) {
				dependencies.add(new Dependency(
						filter("org{}openjdk{}nashorn"), "nashorn-core", versions.getProperty("nashorn"), null, Collections.emptyList()
				));
				dependencies.add(new Dependency(
						filter("org{}ow2{}asm"), "asm", versions.getProperty("asm"), null, Collections.emptyList()
				));
				relocations.add(new RelocationRule(filter("org{}openjdk{}nashorn"), plugin.getPackageName() + ".libs.nashorn"));
				relocations.add(new RelocationRule(filter("org{}objectweb{}asm"), plugin.getPackageName() + ".libs.asm"));
			}
		}
		
		return new DependencyData(
				Collections.emptySet(),
				Collections.singleton(new Repository(new URL(SimpleMirrorSelector.CENTRAL_URL))),
				dependencies,
				relocations
		);
	}
	
	private static String filter(String str) {
		return str.replace("{}", ".");
	}
	
	public enum Usage {
		H2, SQLITE, MYSQL, MARIADB, POSTGRESQL, SCRIPT
	}
}
