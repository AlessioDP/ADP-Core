package com.alessiodp.core.common.configuration;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;

import java.util.List;

public class ConfigTest extends ConfigurationFile {
	
	@ConfigOption(path = "this.is.a-test")
	public static boolean A_TEST;
	@ConfigOption(path = "this.is.b-test")
	public static boolean B_TEST;
	@ConfigOption(path = "this.not-a-test")
	public static String NOT_A_TEST;
	@ConfigOption(path = "this.parameterized-list-double")
	public static List<Double> DOUBLE_LIST;
	
	public ConfigTest(@NonNull ADPPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public void loadDefaults() {
	
	}
	
	@Override
	public void loadConfiguration() {
		loadConfigOptions();
	}
	
	@Override
	public String getFileName() {
		return "test.yml";
	}
	
	@Override
	public String getResourceName() {
		return "configs/test.yml";
	}
	
	@Override
	public int getLatestVersion() {
		return 0;
	}
}
