package com.alessiodp.core.common.storage.sql.sqlite;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.libraries.ILibrary;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

@RequiredArgsConstructor
public class SQLiteDao implements IDatabaseSQL {
	@NonNull private final ADPPlugin plugin;
	@NonNull private final String databaseName;
	
	@Getter private boolean failed;
	
	@Override
	public void initSQL() {
		failed = false;
		if (plugin.getLibraryManager().initLibrary(ILibrary.SQLITE_JDBC)) {
			if (getConnection() == null) {
				failed = true;
			}
		} else
			failed = true;
	}
	
	@Override
	public Connection getConnection() {
		Connection ret = null;
		try {
			Class.forName("org.sqlite.JDBC");
			
			ret = DriverManager.getConnection("jdbc:sqlite:"
					+ plugin.getFolder().resolve(databaseName));
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_SQL_CONNECTIONERROR
					.replace("{storage}", StorageType.SQLITE.getFormattedName())
					.replace("{message}", ex.getMessage()));
		}
		return ret;
	}
	
	@Override
	public void stopSQL() {
		// Nothing to do
	}
}