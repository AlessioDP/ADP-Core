package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import com.alessiodp.core.common.storage.sql.migrations.Migrator;
import com.alessiodp.core.common.storage.sql.migrations.MigratorConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alessiodp.core.common.jpa.Tables.SCHEMA_HISTORY;

@RequiredArgsConstructor
public abstract class SQLDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final StorageType storageType;
	@Getter protected String charset;
	@Getter protected String prefix;
	
	@Getter protected IDatabaseSQL database;
	
	@Override
	public final void stop() {
		if (!isFailed())
			database.stopSQL();
	}
	
	@Override
	public final boolean isFailed() {
		return database == null || database.isFailed();
	}
	
	@Override
	public void init() {
		// Initialize DAO
		database = initDao();
		// Set prefix
		prefix = initPrefix();
		// Set charset
		charset = initCharset();
		
		// Check if initialized
		if (database != null) {
			database.initSQL(getPlaceholders(), charset);
			
			// Check for failures
			if (!database.isFailed()) {
				try {
					MigratorConfiguration migratorConfiguration = prepareMigrator();
					migrateTables(migratorConfiguration.load());
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	/**
	 * Initialize the SQL DAO
	 *
	 * @return the initialized DAO
	 */
	protected abstract IDatabaseSQL initDao();
	
	/**
	 * Initialize the prefix
	 *
	 * @return the initialized prefix
	 */
	protected abstract String initPrefix();
	
	/**
	 * Initialize the charset
	 *
	 * @return the initialized charset
	 */
	protected abstract String initCharset();
	
	/**
	 * Get all SQL tables
	 *
	 * @return a list of SQL tables
	 */
	protected List<Table<?>> getTables() {
		ArrayList<Table<?>> ret = new ArrayList<>();
		ret.add(SCHEMA_HISTORY);
		return ret;
	}
	
	/**
	 * Get all placeholders
	 *
	 * @return a list of placeholders
	 */
	protected Map<String, String> getPlaceholders() {
		HashMap<String, String> ret = new HashMap<>();
		for (Table<?> t : getTables()) {
			ret.put(t.getName(), prefix + t.getName());
		}
		return ret;
	}
	
	/**
	 * Prepare the Migrator class
	 *
	 * @return the Migrator configuration
	 */
	protected MigratorConfiguration prepareMigrator() {
		return Migrator.configure()
				.setQueryBuilder(database.getQueryBuilder())
				.setEncoding(charset)
				.setLocation("db/migrations/" + storageType.name().toLowerCase() + "/")
				.setPlaceholders(Collections.singletonMap("prefix", prefix))
				.setSchemaHistory(prefix + "schema_history")
				.setStartMigration(1)
				.setBackwardMigration(getBackwardMigration());
	}
	
	/**
	 * Start Migrator.migrate
	 *
	 * @param migrator the Migrator to migrate
	 */
	protected void migrateTables(Migrator migrator) {
		migrator.migrate();
	}
	
	/**
	 * Get the backward migration file
	 *
	 * @return The migration file number, -1 to disable
	 */
	protected int getBackwardMigration() {
		return 0;
	}
}