package com.alessiodp.core.bukkit.scheduling;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.scheduling.ADPScheduler;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executor;

public class ADPBukkitScheduler extends ADPScheduler {
	private final Executor bukkitSync;
	
	public ADPBukkitScheduler(ADPPlugin plugin) {
		super(plugin);
		Plugin bukkitPlugin = ((Plugin) plugin.getBootstrap());
		bukkitSync = r -> bukkitPlugin.getServer().getScheduler().scheduleSyncDelayedTask(bukkitPlugin, r);
	}
	
	@Override
	public Executor getSyncExecutor() {
		return bukkitSync;
	}
}