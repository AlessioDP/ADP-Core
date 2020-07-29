package com.alessiodp.core.common.bootstrap;

import com.alessiodp.core.common.addons.ADPLibraryManager;
import com.alessiodp.core.common.logging.ConsoleColor;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class AbstractADPPlugin implements ADPBootstrap {
	private final ADPBootstrap bootstrap;
	
	@Override
	public ADPBootstrap getBootstrap() {
		return bootstrap;
	}
	
	@Override
	public Path getFolder() {
		return bootstrap.getFolder();
	}
	
	@Override
	public String getAuthor() {
		return bootstrap.getAuthor();
	}
	
	@Override
	public String getPlatform() {
		return bootstrap.getPlatform();
	}
	
	@Override
	public String getVersion() {
		return bootstrap.getVersion();
	}
	
	@Override
	public void stopPlugin() {
		bootstrap.stopPlugin();
	}
	
	@Override
	public ADPLibraryManager getLibraryManager() {
		return bootstrap.getLibraryManager();
	}
	
	@Override
	public InputStream getResource(String resource) {
		return bootstrap.getResource(resource);
	}
	
	@Override
	public boolean isPluginEnabled(String pluginName) {
		return bootstrap.isPluginEnabled(pluginName);
	}
	
	@Override
	public User getPlayer(UUID uuid) {
		return bootstrap.getPlayer(uuid);
	}
	
	@Override
	public User getPlayerByName(String name) {
		return bootstrap.getPlayerByName(name);
	}
	
	@Override
	public OfflineUser getOfflinePlayer(UUID uuid) {
		return bootstrap.getOfflinePlayer(uuid);
	}
	
	@Override
	public List<User> getOnlinePlayers() {
		return bootstrap.getOnlinePlayers();
	}
	
	@Override
	public void executeCommand(String command) {
		bootstrap.executeCommand(command);
	}
	
	@Override
	public void executeCommandByUser(String command, User user) {
		bootstrap.executeCommandByUser(command, user);
	}
	
	@Override
	public void logConsole(String message, boolean isWarning) {
		CommonUtils.ifNonEmptyDo(message, () -> bootstrap.logConsole("[" + getConsoleColor().getCode() + getPluginName() + ConsoleColor.RESET.getCode() + "] " + message, isWarning));
	}
	
	/**
	 * Get plugin name
	 *
	 * @return the name of the plugin
	 */
	public abstract String getPluginName();
	
	/**
	 * Get plugin fallback name
	 *
	 * @return the fallback name of the plugin
	 */
	public abstract String getPluginFallbackName();
	
	/**
	 * Get the console color of the plugin
	 *
	 * @return the console color of the plugin
	 */
	protected abstract ConsoleColor getConsoleColor();
	
	/**
	 * Get bStats plugin id
	 *
	 * @return the id of the plugin
	 */
	public abstract int getBstatsId();
	
	/**
	 * Get the package name
	 *
	 * @return the package name
	 */
	public abstract String getPackageName();
}