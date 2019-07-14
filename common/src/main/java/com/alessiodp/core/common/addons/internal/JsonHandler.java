package com.alessiodp.core.common.addons.internal;

public interface JsonHandler {
	/**
	 * Send a json message to the user
	 *
	 * @param user user that receive the message
	 * @param jsonMessage message formatted as json
	 * @return true if the message has been sent
	 */
	boolean sendMessage(Object user, String jsonMessage);
	
	/**
	 * Is the message json formatted?
	 *
	 * @param jsonMessage message formatted as json
	 * @return true if the message is correctly formatted as json
	 */
	boolean isJson(String jsonMessage);
}
