package com.alessiodp.core.common.addons.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public abstract class JsonHandler {
	protected final JsonParser jsonParser;
	
	public JsonHandler() {
		jsonParser = new JsonParser();
	}
	
	/**
	 * Send a json message to the user
	 *
	 * @param user user that receive the message
	 * @param jsonMessage message formatted as json
	 * @return true if the message has been sent
	 */
	public abstract boolean sendMessage(Object user, String jsonMessage);
	
	/**
	 * Is the message json formatted?
	 *
	 * @param jsonMessage message formatted as json
	 * @return true if the message is correctly formatted as json
	 */
	public boolean isJson(String jsonMessage) {
		boolean ret = false;
		try {
			jsonParser.parse(jsonMessage);
			ret = true;
		} catch (JsonParseException ignored) {}
		return ret;
	}
	
	/**
	 * Convert a JSON message into a plain message
	 *
	 * @param jsonMessage message formatted as json
	 * @return the plain message
	 */
	public String removeJson(String jsonMessage) {
		String ret = jsonMessage;
		if (jsonMessage != null) {
			try {
				JsonElement je = jsonParser.parse(jsonMessage);
				String parsedJson = convertToString(je);
				if (parsedJson != null)
					ret = parsedJson;
			} catch (JsonParseException ignored) {
			}
		}
		return ret;
	}
	
	private String convertToString(JsonElement je) {
		if (je instanceof JsonArray) {
			StringBuilder ret = new StringBuilder();
			for (JsonElement j : ((JsonArray) je)) {
				if (j.isJsonObject()) {
					try {
						ret.append(j.getAsJsonObject().get("text").getAsString());
					} catch (Exception ignored) {}
				}
			}
			return ret.toString();
		} else if (je.isJsonObject()) {
			try {
				return je.getAsJsonObject().get("text").getAsString();
			} catch (Exception ignored) {}
		}
		return null;
	}
}
