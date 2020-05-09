package com.alessiodp.core.common.storage.sql.migrator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileParser.class)
public class FileParserTest {
	@Test
	public void testQueryParser() throws IOException {
		FileParser fp = new FileParser(
				getClass().getClassLoader().getResourceAsStream("db/migrations/h2/1__Test_queries.sql"),
				"1__Test_queries.sql"
		);
		assertEquals(
				fp.readStatement(),
				"CREATE TABLE `<prefix>table` ( `column1` INTEGER AUTO_INCREMENT PRIMARY KEY, `column2` VARCHAR(25) NOT NULL )"
		);
		assertEquals(
				fp.readStatement(),
				"SELECT * FROM `<prefix>table`"
		);
		assertEquals(
				fp.readStatement(),
				"ALTER TABLE `<prefix>table` ADD `column3` INTEGER DEFAULT 0 NOT NULL"
		);
		assertNull(fp.readStatement());
	}
	
	@Test
	public void testNameParser() {
		FileParser fp = new FileParser(new InputStream() {
			@Override
			public int read() {
				return 0;
			}
		}, "");
		
		fp.parseName("1__Test_name.sql");
		assertEquals(fp.getVersion(), 1);
		assertEquals(fp.getDescription(), "Test_name");
		
		fp.parseName("101__Complex1Name2.sql");
		assertEquals(fp.getVersion(), 101);
		assertEquals(fp.getDescription(), "Complex1Name2");
		
		try {
			fp.parseName("1_a_Test_name.sql");
			fail("Failed invalid name test case 1");
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().contains("Invalid migration"));
		}
		
		try {
			fp.parseName("__Test_name.sql");
			fail("Failed invalid name test case 2");
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().contains("Invalid migration"));
		}
	}
}
