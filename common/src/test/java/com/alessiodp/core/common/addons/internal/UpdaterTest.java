package com.alessiodp.core.common.addons.internal;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.internal.ADPUpdater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ADPUpdater.class)
public class UpdaterTest {
	private ADPUpdater updater;
	private final static String currentVersion = "2.5.10";
	
	@Before
	public void setUp() {
		ADPPlugin mockPlugin = mock(ADPPlugin.class);
		updater = new ADPUpdater(mockPlugin);
	}
	
	@Test
	public void testCheckVersionMajor() throws Exception {
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0", currentVersion));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"0.0.0", currentVersion));
	}
	
	@Test
	public void testCheckVersionMinor() throws Exception {
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.10.0", currentVersion));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.0.0", currentVersion));
	}
	
	@Test
	public void testCheckVersionPatch() throws Exception {
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.5.20", currentVersion));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.5.0", currentVersion));
	}
	
	@Test
	public void testCheckVersionSnapshot() throws Exception {
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.5.20", currentVersion + "-SNAPSHOT"));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"2.5.0", currentVersion + "-SNAPSHOT"));
	}
	
	@Test
	public void testCheckVersionReleaseCandidate() throws Exception {
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0-rc.1", currentVersion));
		
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0-rc.1", currentVersion + "-rc.1"));
		Assert.assertTrue(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0-rc.3", "3.0.0-rc.2"));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0-rc.2", "3.0.0-rc.2"));
		Assert.assertFalse(Whitebox.<Boolean> invokeMethod(updater, "checkVersion",
				"3.0.0-rc.1", "3.0.0-rc.2"));
	}
}
