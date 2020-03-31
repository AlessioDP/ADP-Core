package com.alessiodp.core.common.storage.sql.mysql;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import com.alessiodp.core.common.configuration.Constants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MySQLDao implements IDatabaseSQL {
	@NonNull private final ADPPlugin plugin;
	@NonNull private final MySQLHikariConfiguration hikariConfiguration;
	
	@Getter private HikariDataSource dataSource;
	@Getter private DSLContext queryBuilder;
	@Getter private boolean failed;
	
	@Override
	public void initSQL(Map<String, String> placeholders, String charset) {
		failed = false;
		try {
			hikariConfiguration.setCharacterEncoding(charset);
			dataSource = hikariConfiguration.setup();
			
			List<MappedTable> mts = new ArrayList<>();
			for (Map.Entry<String, String> e : placeholders.entrySet())
				mts.add(new MappedTable().withInput(e.getKey().toUpperCase()).withOutput(e.getValue()));
			
			queryBuilder = DSL.using(
					dataSource,
					SQLDialect.MYSQL,
					new Settings()
							.withRenderNameCase(RenderNameCase.LOWER)
							.withRenderMapping(new RenderMapping()
									.withSchemata(new MappedSchema()
											.withInputExpression(Pattern.compile(".*"))
											.withTables(mts))
							)
			);
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_MYSQL
					.replace("{message}", ex.getMessage()));
		}
		
		// Test connection
		if (dataSource == null || !ping()) {
			failed = true;
		}
	}
	
	/**
	 * Ping the database
	 *
	 * @return false if the ping fails
	 */
	public boolean ping() {
		boolean ret = false;
		try {
			queryBuilder.execute("/* ping */ SELECT 1;");
			ret = true;
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_SQL_CONNECTIONERROR
					.replace("{storage}", StorageType.MYSQL.getFormattedName())
					.replace("{message}", ex.getMessage()));
		}
		return ret;
	}
	
	@Override
	public void stopSQL() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
		}
		if (queryBuilder != null) {
			queryBuilder.close();
		}
	}
}
