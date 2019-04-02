package com.alessiodp.core.bukkit.addons.internal.json;

import org.bukkit.entity.Player;

public interface JsonHandler {
	/**
	 * Send a json message to the player
	 *
	 * @param player player that receive the message
	 * @param jsonMessage message formatted as json
	 * @return true if the message has been sent
	 */
	boolean sendMessage(Player player, String jsonMessage);
	
	/**
	 * Is the message json formatted?
	 *
	 * @param jsonMessage message formatted as json
	 * @return true if the message is correctly formatted as json
	 */
	boolean isJson(String jsonMessage);
}
