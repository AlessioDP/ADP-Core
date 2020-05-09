package com.alessiodp.core.common.user;

import com.alessiodp.core.common.ADPPlugin;

import java.util.UUID;

public interface OfflineUser {
	/**
	 * Get UUID of the user
	 *
	 * @return the UUID of the user
	 */
	UUID getUUID();
	
	/**
	 * Is the user online?
	 *
	 * @return true if the user is online
	 */
	boolean isOnline();
	
	/**
	 * Is the player an operator?
	 *
	 * @return true if is an operator
	 */
	boolean isOperator();
	
	/**
	 * Get the name of the user
	 *
	 * @return the name of the user
	 */
	String getName();
	
	/**
	 * Get plugin instance
	 *
	 * @return the plugin instance
	 */
	ADPPlugin getPlugin();
}