package com.alessiodp.core.common.storage.sql.migrations;

import lombok.Getter;
import org.jooq.DSLContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MigratorConfiguration {
	@Getter private DSLContext queryBuilder;
	@Getter private String location;
	@Getter private Map<String, String> placeholders = new HashMap<>();
	@Getter private String placeholderPrefix = "${";
	@Getter private String placeholderSuffix = "}";
	@Getter private Charset encoding;
	@Getter private String schemaHistory = "schema_history";
	@Getter private int startMigration = 1;
	@Getter private int backwardMigration = -1;
	
	public MigratorConfiguration setQueryBuilder(DSLContext queryBuilder) {
		this.queryBuilder = queryBuilder;
		return this;
	}
	
	/**
	 * Set scripts location
	 *
	 * @param location the location to set
	 * @return the class instance
	 */
	public MigratorConfiguration setLocation(String location) {
		this.location = location;
		return this;
	}
	
	/**
	 * Set placeholders
	 *
	 * @param placeholders the placeholders to set
	 * @return the class instance
	 */
	public MigratorConfiguration setPlaceholders(Map<String, String> placeholders) {
		this.placeholders = placeholders;
		return this;
	}
	
	/**
	 * Set placeholder prefix
	 *
	 * @param placeholderPrefix the placeholder prefix to set
	 * @return the class instance
	 */
	public MigratorConfiguration setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
		return this;
	}
	
	/**
	 * Set placeholder suffix
	 *
	 * @param placeholderSuffix the placeholder suffix to set
	 * @return the class instance
	 */
	public MigratorConfiguration setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
		return this;
	}
	
	/**
	 * Set the charset encoding
	 *
	 * @param encoding the encoding to set
	 * @return the class instance
	 */
	public MigratorConfiguration setEncoding(String encoding) {
		try {
			this.encoding = Charset.forName(encoding);
		} catch (Exception ignored) {
			return setEncoding(StandardCharsets.UTF_8);
		}
		return this;
	}
	
	/**
	 * Set the charset encoding
	 *
	 * @param encoding the encoding to set
	 * @return the class instance
	 */
	public MigratorConfiguration setEncoding(Charset encoding) {
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * Set the schema history name
	 *
	 * @param schemaHistory the schema history name to set
	 * @return the class instance
	 */
	public MigratorConfiguration setSchemaHistory(String schemaHistory) {
		this.schemaHistory = schemaHistory;
		return this;
	}
	
	/**
	 * Set the start migration table
	 *
	 * @param startMigration the start migration table to set
	 * @return the class instance
	 */
	public MigratorConfiguration setStartMigration(int startMigration) {
		this.startMigration = startMigration;
		return this;
	}
	
	/**
	 * Set the backward migration table (-1 to disable)
	 *
	 * @param backwardMigration the backward migration table to set
	 * @return the class instance
	 */
	public MigratorConfiguration setBackwardMigration(int backwardMigration) {
		this.backwardMigration = backwardMigration;
		return this;
	}
	
	/**
	 * Load a new Migrator with this configuration
	 *
	 * @return a new Migrator instance
	 */
	public Migrator load() {
		return new Migrator(this);
	}
}
