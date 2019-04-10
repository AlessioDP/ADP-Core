package com.alessiodp.core.bungeecord.bootstrap;

import com.alessiodp.core.bungeecord.user.BungeeOfflineUser;
import com.alessiodp.core.bungeecord.user.BungeeUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public abstract class ADPBungeeBootstrap extends Plugin implements ADPBootstrap {
	protected ADPPlugin plugin;
	
	@Override
	public void onEnable() {
		plugin.enabling();
	}
	@Override
	public void onDisable() {
		plugin.disabling();
	}
	
	@Override
	public ADPBootstrap getBootstrap() {
		return this;
	}
	
	@Override
	public Path getFolder() {
		return Paths.get(super.getDataFolder().getAbsolutePath());
	}
	
	@Override
	public String getAuthor() {
		return super.getDescription().getAuthor();
	}
	
	@Override
	public String getVersion() {
		return super.getDescription().getVersion();
	}
	
	@Override
	public void stopPlugin() {
		this.onDisable();
		super.getProxy().getPluginManager().unregisterCommands(this);
		super.getProxy().getPluginManager().unregisterListeners(this);
	}
	
	@Override
	public InputStream getResource(String resource) {
		return super.getResourceAsStream(resource);
	}
	
	@Override
	public User getPlayer(UUID uuid) {
		ProxiedPlayer player = super.getProxy().getPlayer(uuid);
		return player != null ? new BungeeUser(player) : null;
	}
	
	@Override
	public User getPlayerByName(String name) {
		ProxiedPlayer player = super.getProxy().getPlayer(name);
		return player != null ? new BungeeUser(player) : null;
	}
	
	@Override
	public OfflineUser getOfflinePlayer(UUID uuid) {
		ProxiedPlayer player = super.getProxy().getPlayer(uuid);
		return new BungeeOfflineUser(player, uuid);
	}
	
	@Override
	public List<User> getOnlinePlayers() {
		List<User> ret = new ArrayList<>();
		for (ProxiedPlayer player : super.getProxy().getPlayers()) {
			ret.add(new BungeeUser(player));
		}
		return ret;
	}
	
	@Override
	public void logConsole(String message, boolean isWarning) {
		super.getProxy().getLogger().log(isWarning ? Level.WARNING : Level.INFO, message);
	}
}
