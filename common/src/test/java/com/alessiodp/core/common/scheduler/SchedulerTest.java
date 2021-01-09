package com.alessiodp.core.common.scheduler;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.logging.LoggerManager;
import com.alessiodp.core.common.storage.DatabaseManager;
import com.alessiodp.core.common.storage.StorageType;
import com.alessiodp.core.common.storage.interfaces.IDatabaseDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ADPPlugin.class)
public class SchedulerTest {
	DatabaseManager databaseManager;
	
	@Before
	public void setUp() {
		ADPPlugin mockPlugin = mock(ADPPlugin.class);
		LoggerManager mockLoggerManager = mock(LoggerManager.class);
		mockStatic(ADPPlugin.class);
		when(ADPPlugin.getInstance()).thenReturn(mockPlugin);
		when(mockPlugin.getLoggerManager()).thenReturn(mockLoggerManager);
		when(mockPlugin.getFolder()).thenReturn(Paths.get("./"));
		
		databaseManager = new DatabaseManager(mockPlugin) {
			@Override
			protected IDatabaseDispatcher initializeDispatcher(StorageType storageType) {
				return null;
			}
		};
	}
	
	@Test
	public void testSafelyAsync() throws Exception {
		ReentrantLock lock = new ReentrantLock();
		
		Runnable checker = () -> {
			assertFalse(lock.isLocked());
			lock.lock();
			assertTrue(lock.isLocked());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			lock.unlock();
			assertFalse(lock.isLocked());
		};
		
		CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
			try {
				Whitebox.<CompletableFuture<Void>> invokeMethod(databaseManager, "executeSafelyAsync",
						checker).get();
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		});
		CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
			try {
				Whitebox.<CompletableFuture<Void>> invokeMethod(databaseManager, "executeSafelyAsync",
						checker).get();
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		});
		CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> {
			try {
				Whitebox.<CompletableFuture<Void>> invokeMethod(databaseManager, "executeSafelyAsync",
						checker).get();
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		});
		
		cf1.get();
		cf2.get();
		cf3.get();
	}
}
