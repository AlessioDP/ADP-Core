package com.alessiodp.core.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
		ADPPlugin.class,
})
public class LoadPluginTest {
	private ADPPlugin mockPlugin;
	
	@Before
	public void setUp() {
		mockPlugin = mock(ADPPlugin.class);
		when(mockPlugin.canBeLoaded()).thenCallRealMethod();
	}
	
	@Test
	public void testLoadingWithJava11AsLegacy() {
		when(mockPlugin.getJavaVersion()).thenReturn(11);
		when(mockPlugin.isCompiledForJava16()).thenReturn(false);
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(false);
		assertTrue(mockPlugin.canBeLoaded());
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(true);
		assertTrue(mockPlugin.canBeLoaded());
	}
	
	@Test
	public void testLoadingWithJava16AsLegacy() {
		when(mockPlugin.getJavaVersion()).thenReturn(16);
		when(mockPlugin.isCompiledForJava16()).thenReturn(false);
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(false);
		assertFalse(mockPlugin.canBeLoaded());
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(true);
		assertFalse(mockPlugin.canBeLoaded());
	}
	
	@Test
	public void testLoadingWithJava11AsJ16() {
		when(mockPlugin.getJavaVersion()).thenReturn(11);
		when(mockPlugin.isCompiledForJava16()).thenReturn(true);
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(false);
		assertFalse(mockPlugin.canBeLoaded());
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(true);
		assertTrue(mockPlugin.canBeLoaded());
	}
	
	@Test
	public void testLoadingWithJava16AsJ16() {
		when(mockPlugin.getJavaVersion()).thenReturn(16);
		when(mockPlugin.isCompiledForJava16()).thenReturn(true);
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(false);
		assertFalse(mockPlugin.canBeLoaded());
		
		when(mockPlugin.areLibrariesSupported()).thenReturn(true);
		assertTrue(mockPlugin.canBeLoaded());
	}
}
