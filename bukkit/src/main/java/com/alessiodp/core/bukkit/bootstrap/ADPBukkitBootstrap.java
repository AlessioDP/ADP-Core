package com.alessiodp.core.bukkit.bootstrap;

import com.alessiodp.core.bukkit.user.BukkitOfflineUser;
import com.alessiodp.core.bukkit.user.BukkitUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.ADPLibraryManager;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.bootstrap.PluginPlatform;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.Getter;
import lombok.NonNull;
import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public abstract class ADPBukkitBootstrap extends JavaPlugin implements ADPBootstrap {
	@NonNull protected ADPPlugin plugin;
	@Getter private ADPLibraryManager libraryManager;
	
	@Override
	public void onEnable() {
		libraryManager = new ADPLibraryManager(plugin,
				!plugin.isCompiledForJava16() ? new BukkitLibraryManager(this) : null
		);
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
	public PluginPlatform getPlatform() {
		return PluginPlatform.BUKKIT;
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
	public boolean areLibrariesSupported() {
		try {
			Class.forName("org.bukkit.plugin.java.LibraryLoader");
			return true;
		} catch (Exception ignored) {}
		return false;
	}
	
	@Override
	public boolean isPluginEnabled(String pluginName) {
		return super.getServer().getPluginManager().isPluginEnabled(pluginName);
	}
	
	@Override
	public InputStream getResource(String resource) {
		return super.getResource(resource);
	}
	
	@Override
	public User getPlayer(UUID uuid) {
		return CommonUtils.ifNonNullReturn(Bukkit.getPlayer(uuid), (player) -> new BukkitUser(plugin, player), null);
	}
	
	@Override
	public User getPlayerByName(String name) {
		return CommonUtils.ifNonNullReturn(Bukkit.getPlayer(name), (player) -> new BukkitUser(plugin, player), null);
	}
	
	@Override
	public OfflineUser getOfflinePlayer(UUID uuid) {
		return new BukkitOfflineUser(plugin, Bukkit.getOfflinePlayer(uuid));
	}
	
	@Override
	public List<User> getOnlinePlayers() {
		List<User> ret = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			ret.add(new BukkitUser(plugin, player));
		}
		return ret;
	}
	
	@Override
	public void executeCommand(String command) {
		Bukkit.dispatchCommand(getServer().getConsoleSender(), command);
	}
	
	@Override
	public void executeCommandByUser(String command, User user) {
		Bukkit.dispatchCommand(((BukkitUser) user).getSender(), command);
	}
	
	@Override
	public void logConsole(String message, LogLevel logLevel) {
		Level level;
		switch (logLevel) {
			case ERROR:
				level = Level.SEVERE;
				break;
			case WARNING:
				level = Level.WARNING;
				break;
			case INFO:
			default:
				level = Level.INFO;
				break;
		}
		CommonUtils.ifNonEmptyDo(message, () -> super.getServer().getLogger().log(level, message));
	}
	
	/**
	 * Check if is running Spigot
	 *
	 * @return true if Spigot API works
	 */
	public boolean isSpigot() {
		boolean ret = false;
		try {
			Class.forName("net.md_5.bungee.chat.ComponentSerializer");
			ret = true;
		} catch (ClassNotFoundException ignored) {}
		return ret;
	}
}
