package com.alessiodp.core.common.user;

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
	 * Get the name of the user
	 *
	 * @return the name of the user
	 */
	String getName();
}