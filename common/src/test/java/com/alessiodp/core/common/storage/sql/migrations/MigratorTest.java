package com.alessiodp.core.common.storage.sql.migrations;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import org.h2.Driver;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

import static com.alessiodp.core.common.jpa.Tables.SCHEMA_HISTORY;
import static org.jooq.impl.DSL.table;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		Migrator.class,
		ADPPlugin.class,
		LoggerManager.class
})
public class MigratorTest {
	private Migrator migrator;
	private DSLContext queryBuilder;
	
	@Before
	public void setup() throws SQLException {
		System.getProperties().setProperty("org.jooq.no-logo", "TRUE");
		queryBuilder = DSL.using(Driver.load().connect("jdbc:h2:mem:test", new Properties()), SQLDialect.H2);
		migrator = Migrator.configure()
				.setLocation("db/migrations/")
				.setQueryBuilder(queryBuilder)
				.setPlaceholders(Collections.singletonMap("table", "test_table"))
				.load();
		
		ADPPlugin mockPlugin = mock(ADPPlugin.class);
		LoggerManager mockLoggerManager = mock(LoggerManager.class);
		mockStatic(ADPPlugin.class);
		when(ADPPlugin.getInstance()).thenReturn(mockPlugin);
		when(mockPlugin.getLoggerManager()).thenReturn(mockLoggerManager);
		
		when(mockPlugin.getResource(anyString())).thenAnswer((mock) -> getClass().getClassLoader().getResourceAsStream(mock.getArgument(0)));
		when(mockLoggerManager.isDebugEnabled()).thenReturn(true);
	}
	
	@Test
	public void testSearchScripts() {
		assertEquals(migrator.getScripts().size(), 0);
		migrator.searchScripts();
		assertEquals(migrator.getScripts().size(), 2);
	}
	
	@Test
	public void testEmptyDatabase() {
		assertTrue(queryBuilder.meta().getTables(SCHEMA_HISTORY.getName()).isEmpty());
		migrator.migrate();
		assertFalse(queryBuilder.meta().getTables(SCHEMA_HISTORY.getName()).isEmpty());
		
		// Check tables
		assertEquals(queryBuilder.fetchCount(SCHEMA_HISTORY), 2);
		
		assertEquals(queryBuilder.fetchCount(table("test_table")), 1);
	}
}
