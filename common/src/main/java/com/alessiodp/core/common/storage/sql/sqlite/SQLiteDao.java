package com.alessiodp.core.common.storage.sql.sqlite;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import com.alessiodp.core.common.configuration.Constants;
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
import org.sqlite.SQLiteDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SQLiteDao implements IDatabaseSQL {
	@NonNull private final ADPPlugin plugin;
	@NonNull private final String databaseName;
	
	@Getter private SQLiteDataSource dataSource;
	@Getter private DSLContext queryBuilder;
	@Getter private boolean failed;
	
	@Override
	public void initSQL(Map<String, String> placeholders, String charset) {
		failed = false;
		try {
			dataSource = new SQLiteDataSource();
			dataSource.setEncoding(charset);
			dataSource.setUrl("jdbc:sqlite:" + plugin.getFolder().resolve(databaseName));
			
			List<MappedTable> mts = new ArrayList<>();
			placeholders.forEach((key, value) -> {
				mts.add(new MappedTable().withInput(key.toUpperCase()).withOutput(value.toUpperCase()));
			});
			
			queryBuilder = DSL.using(
					dataSource,
					SQLDialect.SQLITE,
					new Settings()
							.withRenderNameCase(RenderNameCase.LOWER)
							.withRenderMapping(new RenderMapping()
									.withSchemata(new MappedSchema()
											.withInputExpression(Pattern.compile(".*"))
											.withTables(mts))
							)
			);
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_SQLITE
					.replace("{message}", ex.getMessage() != null ? ex.getMessage() : "no message"));
			failed = true;
		}
		if (dataSource == null) {
			failed = true;
		}
	}
	
	@Override
	public void stopSQL() {
		if (queryBuilder != null) {
			queryBuilder.close();
		}
	}
}