package com.alessiodp.core.common.storage.dispatchers;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import com.alessiodp.core.common.storage.sql.connection.ConnectionFactory;
import com.alessiodp.core.common.storage.sql.migrator.Migrator;
import com.alessiodp.core.common.storage.sql.migrator.MigratorConfiguration;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;

import java.util.LinkedList;

@RequiredArgsConstructor
public abstract class SQLDispatcher implements IDatabaseDispatcher {
	@NonNull protected final ADPPlugin plugin;
	@NonNull @Getter protected final StorageType storageType;
	
	@Getter protected ConnectionFactory connectionFactory;
	
	@Override
	public final void stop() {
		if (!isFailed())
			connectionFactory.stop();
	}
	
	@Override
	public final boolean isFailed() {
		return connectionFactory == null || connectionFactory.isFailed();
	}
	
	@Override
	public void init() {
		// Initialize DAO
		connectionFactory = initConnectionFactory();
		
		// Check if initialized
		if (connectionFactory != null) {
			connectionFactory.init();
			
			// Check for failures
			if (!connectionFactory.isFailed()) {
				// Setup logger
				connectionFactory.getJdbi().setSqlLogger(new SqlLogger() {
					@Override
					public void logBeforeExecution(StatementContext context) {
						plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_DB_QUERY_EXECUTE, context.getRenderedSql()), true);
					}
				});
				
				try {
					MigratorConfiguration migratorConfiguration = prepareMigrator();
					migrateTables(migratorConfiguration.load(this));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	/**
	 * Initialize the SQL connection factory
	 *
	 * @return the initialized connection factory
	 */
	protected abstract ConnectionFactory initConnectionFactory();
	
	/**
	 * Prepare the Migrator class
	 *
	 * @return the Migrator configuration
	 */
	protected MigratorConfiguration prepareMigrator() {
		return Migrator.configure()
				.setConnectionFactory(connectionFactory)
				.setLocation("db/migrations/" + CommonUtils.toLowerCase(storageType.name()) + "/")
				.setStorageType(storageType)
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
	
	/**
	 * Perform a migration in this database
	 *
	 * @param transaction The database transaction
	 * @param queries The list of queries of the migration
	 * @param version The version of the database
	 */
	public void performMigration(Handle transaction, LinkedList<String> queries, int version) {
		queries.forEach(query -> transaction.execute(query));
	}
}