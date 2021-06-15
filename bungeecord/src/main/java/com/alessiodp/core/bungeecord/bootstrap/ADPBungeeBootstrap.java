package com.alessiodp.core.bungeecord.bootstrap;

import com.alessiodp.core.bungeecord.user.BungeeOfflineUser;
import com.alessiodp.core.bungeecord.user.BungeeUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.addons.ADPLibraryManager;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.bootstrap.PluginPlatform;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.Getter;
import lombok.NonNull;
import net.byteflux.libby.BungeeLibraryManager;
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
	@NonNull protected ADPPlugin plugin;
	@Getter private ADPLibraryManager libraryManager;
	
	@Override
	public void onEnable() {
		libraryManager = new ADPLibraryManager(plugin,
				!plugin.isCompiledForJava16() ? new BungeeLibraryManager(this): null
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
		return super.getDescription().getAuthor();
	}
	
	@Override
	public PluginPlatform getPlatform() {
		return PluginPlatform.BUNGEECORD;
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
	public boolean areLibrariesSupported() {
		try {
			Class.forName("net.md_5.bungee.api.plugin.LibraryLoader");
			return true;
		} catch (Exception ignored) {}
		return false;
	}
	
	@Override
	public boolean isPluginEnabled(String pluginName) {
		return super.getProxy().getPluginManager().getPlugin(pluginName) != null;
	}
	
	@Override
	public InputStream getResource(String resource) {
		return super.getResourceAsStream(resource);
	}
	
	@Override
	public User getPlayer(UUID uuid) {
		return CommonUtils.ifNonNullReturn(super.getProxy().getPlayer(uuid), (player) -> new BungeeUser(plugin, player), null);
	}
	
	@Override
	public User getPlayerByName(String name) {
		return CommonUtils.ifNonNullReturn(super.getProxy().getPlayer(name), (player) -> new BungeeUser(plugin, player), null);
	}
	
	@Override
	public OfflineUser getOfflinePlayer(UUID uuid) {
		return new BungeeOfflineUser(plugin, super.getProxy().getPlayer(uuid), uuid);
	}
	
	@Override
	public List<User> getOnlinePlayers() {
		List<User> ret = new ArrayList<>();
		for (ProxiedPlayer player : super.getProxy().getPlayers()) {
			ret.add(new BungeeUser(plugin, player));
		}
		return ret;
	}
	
	@Override
	public void executeCommand(String command) {
		getProxy().getPluginManager().dispatchCommand(getProxy().getConsole(), command);
	}
	
	@Override
	public void executeCommandByUser(String command, User user) {
		getProxy().getPluginManager().dispatchCommand(((BungeeUser) user).getSender(), command);
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
		CommonUtils.ifNonEmptyDo(message, () -> super.getProxy().getLogger().log(level, message));
	}
}
