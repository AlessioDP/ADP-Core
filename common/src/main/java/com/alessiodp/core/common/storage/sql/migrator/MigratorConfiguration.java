package com.alessiodp.core.common.storage.sql.migrator;

import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.dispatchers.SQLDispatcher;
import com.alessiodp.core.common.storage.sql.connection.ConnectionFactory;
import lombok.Getter;

public class MigratorConfiguration {
	@Getter private ConnectionFactory connectionFactory;
	@Getter private String location;
	@Getter private StorageType storageType;
	@Getter private int startMigration = 1;
	@Getter private int backwardMigration = -1;
	
	public MigratorConfiguration setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
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
	 * Set the storage type
	 *
	 * @param storageType the storage type to set
	 * @return the class instance
	 */
	public MigratorConfiguration setStorageType(StorageType storageType) {
		this.storageType = storageType;
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
	public Migrator load(SQLDispatcher dispatcher) {
		return new Migrator(dispatcher, this);
	}
}
