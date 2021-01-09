package com.alessiodp.core.common.addons.internal;

public abstract class TitleHandler {
	/**
	 * Send a title message to the user
	 *
	 * @param user user that receive the message
	 * @param titleMessage message to sent
	 * @param fadeInTime fade in time in milliseconds
	 * @param showTime show time in milliseconds
	 * @param fadeOutTime fade out time in milliseconds
	 * @return true if the message has been sent
	 */
	public abstract boolean sendTitle(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime);
	
	/**
	 * Send a json title message to the user
	 *
	 * @param user user that receive the message
	 * @param titleMessage message to sent
	 * @param fadeInTime fade in time in milliseconds
	 * @param showTime show time in milliseconds
	 * @param fadeOutTime fade out time in milliseconds
	 * @return true if the message has been sent
	 */
	public abstract boolean sendTitleJson(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime);
}
