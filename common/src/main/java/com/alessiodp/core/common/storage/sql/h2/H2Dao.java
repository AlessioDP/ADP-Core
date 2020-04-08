package com.alessiodp.core.common.storage.sql.h2;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.storage.interfaces.IDatabaseSQL;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class H2Dao implements IDatabaseSQL {
	@NonNull private final ADPPlugin plugin;
	@NonNull private final String databaseName;
	
	@Getter private JdbcDataSource dataSource;
	@Getter private DSLContext queryBuilder;
	@Getter private boolean failed;
	
	@Override
	public void initSQL(Map<String, String> placeholders, String charset) {
		failed = false;
		try {
			dataSource = new JdbcDataSource();
			dataSource.setUrl("jdbc:h2:" + plugin.getFolder().resolve(databaseName));
			
			List<MappedTable> mts = new ArrayList<>();
			placeholders.forEach((key, value) -> {
				mts.add(new MappedTable().withInput(key.toUpperCase()).withOutput(value.toUpperCase()));
			});
			
			queryBuilder = DSL.using(
					dataSource,
					SQLDialect.H2,
					new Settings()
							.withRenderMapping(new RenderMapping()
									.withSchemata(new MappedSchema()
											.withInputExpression(Pattern.compile(".*"))
											.withTables(mts))
							)
			);
		} catch (Exception ex) {
			ex.printStackTrace();
			plugin.getLoggerManager().printError(Constants.DEBUG_DB_INIT_FAILED_DRIVER
					.replace("{storage}", "H2")
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