package com.alessiodp.core.common.utils;

public interface IColorUtils {
	/**
	 * Check if the given color name exists
	 *
	 * @param colorName the color name to check
	 * @return true if exists
	 */
	boolean existsColor(String colorName);
	
	/**
	 * Convert the given color name
	 *
	 * @param colorName the color name to convert
	 * @return the string with color code or empty if not valid
	 */
	String convertColorByName(String colorName);
	
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
