package com.alessiodp.core.bungeecord.scheduling;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.scheduling.ADPScheduler;
import com.alessiodp.core.common.scheduling.CancellableTask;
import lombok.NonNull;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

// BungeeCord is thread safe, just run task directly on Bungee thread
public class ADPBungeeScheduler extends ADPScheduler {
	private final Executor bungeeSync;
	
	public ADPBungeeScheduler(ADPPlugin plugin) {
		super(plugin);
		Plugin bungeePlugin = ((Plugin) plugin.getBootstrap());
		bungeeSync = r -> bungeePlugin.getProxy().getScheduler().runAsync(bungeePlugin, r);
	}
	
	@Override
	public Executor getAsyncExecutor() {
		return bungeeSync;
	}
	
	@Override
	public Executor getSyncExecutor() {
		return bungeeSync;
	}
	
	
	@Override
	public CancellableTask scheduleAsyncLater(@NonNull Runnable runnable, long delay, TimeUnit unit) {
		Plugin bungeePlugin = ((Plugin) plugin.getBootstrap());
		// Use BungeeCord scheduler instead of mine
		ScheduledTask future = bungeePlugin.getProxy().getScheduler().schedule(bungeePlugin, runnable, delay, unit);
		return future::cancel;
	}
	
	@Override
	public CancellableTask scheduleAsyncRepeating(@NonNull Runnable runnable, long delay, long period, TimeUnit unit) {
		Plugin bungeePlugin = ((Plugin) plugin.getBootstrap());
		// Use BungeeCord scheduler instead of mine
		ScheduledTask future = bungeePlugin.getProxy().getScheduler().schedule(bungeePlugin, runnable, delay, period, unit);
		return future::cancel;
	}
}