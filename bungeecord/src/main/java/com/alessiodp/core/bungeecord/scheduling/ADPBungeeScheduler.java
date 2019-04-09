package com.alessiodp.core.bungeecord.scheduling;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.scheduling.ADPScheduler;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.Executor;

public class ADPBungeeScheduler extends ADPScheduler {
	private final Executor bungeeSync;
	
	public ADPBungeeScheduler(ADPPlugin plugin) {
		super(plugin);
		Plugin bungeePlugin = ((Plugin) plugin.getBootstrap());
		bungeeSync = r -> bungeePlugin.getProxy().getScheduler().runAsync(bungeePlugin, r);
	}
	
	@Override
	public Executor getSyncExecutor() {
		return bungeeSync;
	}
}