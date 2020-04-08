package com.alessiodp.core.common.storage.sql.migrations;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.NonNull;
import org.jooq.Record1;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.simpleyaml.utils.Validate;

import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alessiodp.core.common.jpa.Tables.SCHEMA_HISTORY;
import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.max;

public class Migrator {
	private MigratorConfiguration configuration;
	@Getter private LinkedList<FileParser> scripts;
	
	public Migrator(@NonNull MigratorConfiguration configuration) {
		this.configuration = configuration;
		scripts = new LinkedList<>();
	}
	
	/**
	 * Initialize a new MigratorConfiguration
	 *
	 * @return an initialized MigratorConfiguration
	 */
	public static MigratorConfiguration configure() {
		return new MigratorConfiguration();
	}
	
	/**
	 * Search for every script in the location
	 */
	public void searchScripts() {
		try {
			Reflections reflections = new Reflections(configuration.getLocation(), new ResourcesScanner());
			for (String resource : reflections.getResources(Pattern.compile(".*\\.sql"))) {
				String name = resource.substring(resource.lastIndexOf("/") + 1);
				FileParser fp = new FileParser(
						ADPPlugin.getInstance().getResource(resource),
						name
				);
				fp.parse();
				scripts.add(fp);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Start the migration
	 */
	public void migrate() {
		Validate.notNull(configuration, "The migrator configuration cannot be null");
		Validate.notNull(configuration.getQueryBuilder(), "The migrator query builder cannot be null");
		searchScripts();
		try {
			configuration.getQueryBuilder().transaction(cfg -> {
				int version = 0;
				try {
					Record1<Integer> r = DSL.using(cfg)
							.select(max(SCHEMA_HISTORY.VERSION))
							.from(SCHEMA_HISTORY)
							.fetchAny();
					if (r != null) {
						version = r.value1();
					}
				} catch (DataAccessException ignored) {
					// Create history
					DSL.using(cfg)
							.createTableIfNotExists(SCHEMA_HISTORY)
							.column(SCHEMA_HISTORY.ID, SQLDataType.INTEGER.identity(true))
							.column(SCHEMA_HISTORY.VERSION, SQLDataType.INTEGER.nullable(false))
							.column(SCHEMA_HISTORY.DESCRIPTION, SQLDataType.VARCHAR.length(25).nullable(false))
							.column(SCHEMA_HISTORY.SCRIPT_NAME, SQLDataType.VARCHAR.length(25).nullable(false))
							.column(SCHEMA_HISTORY.INSTALL_DATE, SQLDataType.BIGINT.nullable(false))
							.constraint(
									constraint(
											"PK_" +
													SCHEMA_HISTORY.getName())
											.primaryKey(
													SCHEMA_HISTORY.ID
															.getName()))
							.execute();
				}
				
				// Check for old migrations
				if (configuration.getBackwardMigration() >= 0) {
					Optional<FileParser> fp = scripts.stream()
							.filter((c) -> c.getVersion() == configuration.getBackwardMigration())
							.findFirst();
					if (fp.isPresent()) {
						fp.get().getQueries().forEach(query -> DSL.using(cfg).execute(parsePlaceholders(query)));
						version = configuration.getBackwardMigration();
					} else {
						throw new MissingMigrationTableException(String.format("Missing migration table number %d for backward compatibility", configuration.getBackwardMigration()));
					}
				}
				
				for (FileParser fp : scripts) {
					if (fp.getVersion() > version && fp.getVersion() >= configuration.getStartMigration()) {
						ADPPlugin.getInstance().getLoggerManager().logDebug(Constants.DEBUG_DB_MIGRATOR_MIGRATING
								.replace("{file}", fp.getScriptName()), true);
						fp.getQueries().forEach(query -> DSL.using(cfg).execute(parsePlaceholders(query)));
						
						DSL.using(cfg)
								.insertInto(SCHEMA_HISTORY,
										SCHEMA_HISTORY.VERSION, SCHEMA_HISTORY.DESCRIPTION,
										SCHEMA_HISTORY.SCRIPT_NAME, SCHEMA_HISTORY.INSTALL_DATE)
								.values(
										fp.getVersion(),
										fp.getDescription(),
										fp.getScriptName(),
										System.currentTimeMillis() / 1000
								).execute();
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Parse the query with every placeholders
	 *
	 * @param query the query to parse
	 * @return the parsed query
	 */
	public String parsePlaceholders(String query) {
		String ret = query;
		Matcher matcher = Pattern.compile(
				Pattern.quote(configuration.getPlaceholderPrefix())
						+ "([a-z0-9]+)"
						+ Pattern.quote(configuration.getPlaceholderSuffix()),
				Pattern.CASE_INSENSITIVE).matcher(query);
		while (matcher.find()) {
			String placeholder = configuration.getPlaceholders().get(matcher.group(1));
			if (placeholder != null) {
				ret = ret.replace(matcher.group(), placeholder);
			}
		}
		return ret;
	}
	
	/**
	 * Exception generated by missing migration table
	 */
	public static class MissingMigrationTableException extends RuntimeException {
		MissingMigrationTableException(String text) {
			super(text);
		}
	}
}
