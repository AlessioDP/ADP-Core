package com.alessiodp.core.common.storage.interfaces;

import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.dispatchers.SQLDispatcher;
import com.alessiodp.core.common.storage.sql.ISQLTable;

import java.sql.Connection;

public interface ISQLUpgradeManager {
	/**
	 * Check if the table is outdated
	 *
	 * @param dispatcher the sql dispatcher to handle
	 * @param connection the connection to use
	 * @param table the table to handle
	 * @param databaseType the database type to handle
	 */
	void checkUpgrades(SQLDispatcher dispatcher, Connection connection, ISQLTable table, StorageType databaseType);
}
