package com.alessiodp.core.common.bootstrap;

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
	 * Get resource from plugin resources
	 *
	 * @param resource the resource name
	 * @return the resource found
	 */
	InputStream getResource(String resource);
	
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
	 * Send a message to the console
	 *
	 * @param message the message to send
	 * @param isWarning true if the message is a warning
	 */
	void logConsole(String message, boolean isWarning);
}
