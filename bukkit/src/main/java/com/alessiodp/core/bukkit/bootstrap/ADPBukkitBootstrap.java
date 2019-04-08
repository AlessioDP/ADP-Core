package com.alessiodp.core.bukkit.bootstrap;

import com.alessiodp.core.bukkit.user.BukkitOfflineUser;
import com.alessiodp.core.bukkit.user.BukkitUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public abstract class ADPBukkitBootstrap extends JavaPlugin implements ADPBootstrap {
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
		return super.getDescription().getAuthors().get(0);
	}
	
	@Override
	public String getVersion() {
		return super.getDescription().getVersion();
	}
	
	@Override
	public void stopPlugin() {
		super.getPluginLoader().disablePlugin(this);
	}
	
	@Override
	public User getPlayer(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		return player != null ? new BukkitUser(player) : null;
	}
	
	@Override
	public User getPlayerByName(String name) {
		Player player = Bukkit.getPlayer(name);
		return player != null ? new BukkitUser(player) : null;
	}
	
	@Override
	public OfflineUser getOfflinePlayer(UUID uuid) {
		return new BukkitOfflineUser(Bukkit.getOfflinePlayer(uuid));
	}
	
	@Override
	public List<User> getOnlinePlayers() {
		List<User> ret = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			ret.add(new BukkitUser(player));
		}
		return ret;
	}
	
	@Override
	public void logConsole(String message, boolean isWarning) {
		super.getServer().getLogger().log(isWarning ? Level.WARNING : Level.INFO, message);
	}
}
