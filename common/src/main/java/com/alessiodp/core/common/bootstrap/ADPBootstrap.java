package com.alessiodp.core.common.bootstrap;

import com.alessiodp.core.common.addons.ADPLibraryManager;
import com.alessiodp.core.common.user.OfflineUser;
import com.alessiodp.core.common.user.User;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface ADPBootstrap {
	/**
	 * Get bootstrap
	 *
	 * @return the bootstrap
	 */
	ADPBootstrap getBootstrap();
	
	/**
	 * Get plugin folder
	 *
	 * @return the plugin folder path
	 */
	Path getFolder();
	
	/**
	 * Get plugin author
	 *
	 * @return the author of the plugin
	 */
	String getAuthor();
	
	/**
	 * Get used plugin platform (e.g. Bukkit, BungeeCord)
	 *
	 * @return the platform name
	 */
	String getPlatform();
	
	/**
	 * Get plugin version
	 *
	 * @return the version of the plugin
	 */
	String getVersion();
	
	/**
	 * Stop the plugin
	 */
	void stopPlugin();
	
	/**
	 * Get the library manager
	 *
	 * @return the library manager
	 */
	ADPLibraryManager getLibraryManager();
	
	/**
	 * Get resource from plugin resources
	 *
	 * @param resource the resource name
	 * @return the resource found
	 */
	InputStream getResource(String resource);
	
	/**
	 * Is the plugin enabled?
	 *
	 * @param pluginName the name of the plugin
	 * @return true if the given plugin is enabled
	 */
	boolean isPluginEnabled(String pluginName);
	
	/**
	 * Get player with given uuid
	 *
	 * @param uuid uuid of the player
	 * @return the player found
	 */
	User getPlayer(UUID uuid);
	
	/**
	 * Get player with given name
	 *
	 * @param name name of the player
	 * @return the player found
	 */
	User getPlayerByName(String name);
	
	/**
	 * Get offline player with given uuid
	 *
	 * @param uuid uuid of the player
	 * @return the offline player found
	 */
	OfflineUser getOfflinePlayer(UUID uuid);
	
	/**
	 * Get online players list
	 *
	 * @return a list of online players
	 */
	List<User> getOnlinePlayers();
	
	/**
	 * Execute the command via console
	 * @param command the command to execute
	 */
	void executeCommand(String command);
	
	/**
	 * Execute the command via console
	 * @param command the command to execute
	 * @param user the user executor
	 */
	void executeCommandByUser(String command, User user);
	
	/**
	 * Send a message to the console
	 *
	 * @param message the message to send
	 * @param isWarning true if the message is a warning
	 */
	void logConsole(String message, boolean isWarning);
}
