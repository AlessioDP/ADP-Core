package com.alessiodp.core.common.storage.sql.migrator;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.dispatchers.SQLDispatcher;
import com.alessiodp.core.common.storage.sql.connection.ConnectionFactory;
import com.alessiodp.core.common.storage.sql.connection.H2ConnectionFactory;
import com.alessiodp.core.common.storage.sql.connection.PostgreSQLConnectionFactory;
import com.alessiodp.core.common.storage.sql.connection.SQLiteConnectionFactory;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryDao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryH2Dao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryMariaDBDao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryMySQLDao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryPostgreSQLDao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistorySQLiteDao;
import com.alessiodp.core.common.utils.CommonUtils;
import org.jdbi.v3.core.Handle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		ADPPlugin.class,
})
public class MigratorTest {
	private ADPPlugin mockPlugin;
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void setUp() {
		mockPlugin = mock(ADPPlugin.class);
		LoggerManager mockLoggerManager = mock(LoggerManager.class);
		mockStatic(ADPPlugin.class);
		when(ADPPlugin.getInstance()).thenReturn(mockPlugin);
		when(mockPlugin.getLoggerManager()).thenReturn(mockLoggerManager);
		when(mockPlugin.getFolder()).thenReturn(Paths.get("./"));
		when(mockPlugin.getResource(anyString())).thenAnswer((mock) -> getClass().getClassLoader().getResourceAsStream(mock.getArgument(0)));
		when(mockLoggerManager.isDebugEnabled()).thenReturn(true);
	}
	
	private ConnectionFactory getConnectionFactoryH2() {
		H2ConnectionFactory ret = new H2ConnectionFactory(null, "jdbc:h2:mem:" + UUID.randomUUID().toString() + ";DB_CLOSE_DELAY=-1");
		ret.setTablePrefix("test_");
		ret.init();
		return ret;
	}
	
	private ConnectionFactory getConnectionFactorySQLite() throws IOException {
		SQLiteConnectionFactory ret = new SQLiteConnectionFactory(null, testFolder.newFile("database.db").toPath());
		ret.setTablePrefix("test_");
		ret.init();
		return ret;
	}
	
	private ConnectionFactory getConnectionFactoryMySQL() {
		// Manual test only
		/*
		MySQLConnectionFactory ret = new MySQLConnectionFactory();
		ret.setTablePrefix("test_");
		ret.setServerName("localhost");
		ret.setPort("3306");
		ret.setDatabaseName("database");
		ret.setUsername("root");
		ret.setPassword("");
		ret.init();
		return ret;*/
		return null;
	}
	
	private ConnectionFactory getConnectionFactoryMariaDB() {
		// Manual test only
		/*
		MariaDBConnectionFactory ret = new MariaDBConnectionFactory();
		ret.setTablePrefix("test_");
		ret.setServerName("localhost");
		ret.setPort("3306");
		ret.setDatabaseName("database");
		ret.setUsername("root");
		ret.setPassword("");
		ret.init();
		return ret;*/
		return null;
	}
	
	private ConnectionFactory getConnectionFactoryPostgreSQL() {
		// Manual test only
		PostgreSQLConnectionFactory ret = new PostgreSQLConnectionFactory();
		ret.setTablePrefix("test_");
		ret.setServerName("localhost");
		ret.setPort("5432");
		ret.setDatabaseName("database");
		ret.setUsername("postgres");
		ret.setPassword("");
		ret.init();
		return ret;
		//return null;
	}
	
	private Migrator prepareMigrator(ConnectionFactory cf, StorageType storageType) {
		if (cf == null)
			return null;
		return Migrator.configure()
				.setLocation("db/migrations/" + CommonUtils.toLowerCase(storageType.name()) + "/")
				.setConnectionFactory(cf)
				.setStorageType(storageType)
				.load(new SQLDispatcher(mockPlugin, storageType) {
					@Override
					protected ConnectionFactory initConnectionFactory() {
						return null;
					}
				});
	}
	
	@Test
	public void testSearchScripts() throws IOException {
		searchScripts(prepareMigrator(getConnectionFactoryH2(), StorageType.H2));
		
		searchScripts(prepareMigrator(getConnectionFactorySQLite(), StorageType.SQLITE));
		
		searchScripts(prepareMigrator(getConnectionFactoryMySQL(), StorageType.MYSQL));
		
		searchScripts(prepareMigrator(getConnectionFactoryMariaDB(), StorageType.MARIADB));
		
		searchScripts(prepareMigrator(getConnectionFactoryPostgreSQL(), StorageType.POSTGRESQL));
	}
	
	private void searchScripts(Migrator migrator) {
		if (migrator != null) {
			assertEquals(migrator.getScripts().size(), 0);
			migrator.searchScripts();
			assertEquals(migrator.getScripts().size(), 2);
		}
	}
	
	@Test
	public void testEmptyDatabase() throws IOException {
		ConnectionFactory cf = getConnectionFactoryH2();
		Handle handle = cf.getJdbi().open();
		emptyDatabase(handle, cf.getJdbi().onDemand(SchemaHistoryH2Dao.class), prepareMigrator(cf, StorageType.H2));
		handle.close();
		
		cf = getConnectionFactorySQLite();
		handle = cf.getJdbi().open();
		emptyDatabase(handle, cf.getJdbi().onDemand(SchemaHistorySQLiteDao.class), prepareMigrator(cf, StorageType.SQLITE));
		handle.close();
		
		cf = getConnectionFactoryMySQL();
		if (cf != null) {
			handle = cf.getJdbi().open();
			handle.createUpdate("DROP TABLE IF EXISTS `<prefix>schema_history`, `<prefix>table`;").execute();
			emptyDatabase(handle, cf.getJdbi().onDemand(SchemaHistoryMySQLDao.class), prepareMigrator(cf, StorageType.MYSQL));
			handle.close();
		}
		
		cf = getConnectionFactoryMariaDB();
		if (cf != null) {
			handle = cf.getJdbi().open();
			handle.createUpdate("DROP TABLE IF EXISTS `<prefix>schema_history`, `<prefix>table`;").execute();
			emptyDatabase(handle, cf.getJdbi().onDemand(SchemaHistoryMariaDBDao.class), prepareMigrator(cf, StorageType.MARIADB));
			handle.close();
		}
		
		cf = getConnectionFactoryPostgreSQL();
		if (cf != null) {
			handle = cf.getJdbi().open();
			handle.createUpdate("DROP TABLE IF EXISTS <prefix>schema_history, <prefix>table;").execute();
			emptyDatabase(handle, cf.getJdbi().onDemand(SchemaHistoryPostgreSQLDao.class), prepareMigrator(cf, StorageType.POSTGRESQL));
			handle.close();
		}
	}
	
	private void emptyDatabase(Handle handle, SchemaHistoryDao dao, Migrator migrator) throws IOException {
		migrator.migrate();
		
		// Check tables
		assertEquals(dao.countVersions(), 2);
		
		assertEquals((int) handle.createQuery("SELECT COUNT(*) FROM <prefix>table").mapTo(Integer.class).first(), 1);
		
		// Add a new migration
		String migrationTable = "INSERT INTO <prefix>table (column2, column3) VALUES ('test2', 20);";
		FileParser fp = new FileParser(new ByteArrayInputStream(migrationTable.getBytes(StandardCharsets.UTF_8)), "3__Update" +
				".sql");
		fp.parse();
		migrator.getScripts().add(fp);
		migrator.migrate();
		
		// Check tables
		assertEquals(dao.countVersions(), 3);
		
		assertEquals((int) handle.createQuery("SELECT COUNT(*) FROM <prefix>table").mapTo(Integer.class).first(), 2);
	}
}
