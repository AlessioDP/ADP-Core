package com.alessiodp.core.common.utils;

public interface IColorUtils {
	/**
	 * Translate chat colors
	 *
	 * @param message the message to translate colors
	 * @return the parsed message
	 */
	String convertColors(String message);
	
	/**
	 * Remove chat colors
	 *
	 * @param message the message to remove colors
	 * @return the parsed message
	 */
	String removeColors(String message);
}
