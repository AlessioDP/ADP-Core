package com.alessiodp.core.common.storage.sql.migrator;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import com.alessiodp.core.common.storage.sql.connection.ConnectionFactory;
import com.alessiodp.core.common.storage.sql.connection.H2ConnectionFactory;
import com.alessiodp.core.common.storage.sql.connection.SQLiteConnectionFactory;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryDao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistoryH2Dao;
import com.alessiodp.core.common.storage.sql.dao.SchemaHistorySQLiteDao;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		ADPPlugin.class,
})
public class StorageTest {
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void setUp() {
		ADPPlugin mockPlugin = mock(ADPPlugin.class);
		LoggerManager mockLoggerManager = mock(LoggerManager.class);
		mockStatic(ADPPlugin.class);
		when(ADPPlugin.getInstance()).thenReturn(mockPlugin);
		when(mockPlugin.getLoggerManager()).thenReturn(mockLoggerManager);
		when(mockPlugin.getFolder()).thenReturn(Paths.get("./"));
	}
	
	private ConnectionFactory getConnectionFactoryH2() {
		H2ConnectionFactory ret = new H2ConnectionFactory(null, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
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
	
	@Test
	public void testCreate() throws IOException {
		ConnectionFactory cf = getConnectionFactoryH2();
		create(cf.getJdbi().onDemand(SchemaHistoryH2Dao.class));
		cf.stop();
		
		cf = getConnectionFactorySQLite();
		create(cf.getJdbi().onDemand(SchemaHistorySQLiteDao.class));
		cf.stop();
	}
	
	private void create(SchemaHistoryDao dao) {
		dao.create();
		assertEquals(dao.higherVersion(), 0);
		
		dao.drop();
	}
	
	@Test
	public void testInsert() throws IOException {
		ConnectionFactory cf = getConnectionFactoryH2();
		insert(cf.getJdbi().onDemand(SchemaHistoryH2Dao.class));
		cf.stop();
		
		cf = getConnectionFactorySQLite();
		insert(cf.getJdbi().onDemand(SchemaHistorySQLiteDao.class));
		cf.stop();
	}
	
	private void insert(SchemaHistoryDao dao) {
		dao.create();
		
		dao.insert(1, "test", "test2", 1);
		assertEquals(dao.higherVersion(), 1);
		
		dao.insert(2, "test", "test2", 1);
		dao.insert(3, "test", "test2", 1);
		assertEquals(dao.higherVersion(), 3);
		
		dao.drop();
	}
}