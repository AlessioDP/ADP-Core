package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
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
@PrepareForTest({
		ConfigurationFile.class
})
public class ConfigurationFileTest {
	private ADPPlugin plugin;
	@Rule
	private TemporaryFolder testFolder = new TemporaryFolder();
	
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
		assertEquals(8, countLines(config.getConfiguration().saveToStringWithComments()));
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
