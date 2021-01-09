package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ConfigurationFileTest {
	private ADPPlugin plugin;
	@Rule
	private final TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void setUp() {
		plugin = mock(ADPPlugin.class);
		when(plugin.getResource(anyString())).thenAnswer((a) -> ClassLoader.getSystemResourceAsStream(a.getArgument(0)));
	}
	
	@Test
	public void testDefaultLoad() {
		YamlFile yaml = YamlFile.loadConfiguration(new InputStreamReader(plugin.getResource("configs/test.yml")));
		assertNotNull(yaml);
		validateYaml(yaml);
	}
	
	@Test
	public void testLoad() throws IOException {
		ConfigTest config = new ConfigTest(plugin);
		config.initializeConfiguration(testFolder.getRoot().toPath());
		assertTrue(config.exists());
		config.loadConfiguration();
		
		validateYaml(config.getConfiguration());
		assertEquals(11, countLines(config.getConfiguration().saveToString()));
	}
	
	@Test
	public void testSave() {
		ConfigTest config = new ConfigTest(plugin);
		config.initializeConfiguration(testFolder.getRoot().toPath());
		assertTrue(config.exists());
		config.loadConfiguration();
		ConfigTest.A_TEST = false;
		
		config.save();
		
		assertFalse(config.getConfiguration().getBoolean("this.is.a-test"));
	}
	
	@Test
	public void testLoadConfiguration() {
		ConfigTest config = new ConfigTest(plugin);
		config.initializeConfiguration(testFolder.getRoot().toPath());
		assertTrue(config.exists());
		config.loadConfiguration();
		
		assertTrue(ConfigTest.A_TEST);
		assertFalse(ConfigTest.B_TEST);
		assertEquals("test", ConfigTest.NOT_A_TEST);
		assertEquals((Double) 10.0D, ConfigTest.DOUBLE_LIST.get(0));
		assertEquals((Double) 20.0D, ConfigTest.DOUBLE_LIST.get(1));
	}
	
	private void validateYaml(YamlFile yaml) {
		assertTrue(yaml.getBoolean("this.is.a-test"));
		assertFalse(yaml.getBoolean("this.is.b-test"));
		assertEquals("test", yaml.getString("this.not-a-test"));
	}
	
	private int countLines(String str){
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}
	
}
