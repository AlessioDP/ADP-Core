package com.alessiodp.core.bukkit.events;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.events.EventDispatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class BukkitEventDispatcher implements EventDispatcher {
	@NonNull private final ADPPlugin plugin;
	
	@Override
	public void callEvent(Object event) {
		if (event instanceof Event) {
			if (!((Event) event).isAsynchronous() && !Bukkit.isPrimaryThread()) {
				CountDownLatch cl = new CountDownLatch(1);
				plugin.getScheduler().getSyncExecutor().execute(() -> {
					try {
						Bukkit.getPluginManager().callEvent((Event) event);
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						cl.countDown();
					}
				});
				try {
					cl.await(3000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			} else {
				Bukkit.getPluginManager().callEvent((Event) event);
			}
		}
	}
}
