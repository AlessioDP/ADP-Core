package com.alessiodp.core.common.utils;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Color {
	BLACK('0'),
	DARK_BLUE('1'),
	DARK_GREEN('2'),
	DARK_AQUA('3'),
	DARK_RED('4'),
	DARK_PURPLE('5'),
	GOLD('6'),
	GRAY('7'),
	DARK_GRAY('8'),
	BLUE('9'),
	GREEN('a'),
	AQUA('b'),
	RED('c'),
	LIGHT_PURPLE('d'),
	YELLOW('e'),
	WHITE('f'),
	MAGIC('k', true),
	BOLD('l', true),
	STRIKETHROUGH('m', true),
	UNDERLINE('n', true),
	ITALIC('o', true),
	RESET('r', true),
	HEX('x', true);
	
	@Getter private final char code;
	private final boolean isFormat;
	private final String toString;
	private final String toStringSimple;
	
	public static final char COLOR_CHAR = '\u00A7';
	public static final char HEX_CHAR = '\u0023';
	private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile( "(?i)" + COLOR_CHAR + "[0-9A-FK-ORX]" );
	private static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
	private static final Map<Character, Color> BY_CHAR = Maps.newHashMap();
	
	static {
		for (Color color : values()) {
			BY_CHAR.put(color.code, color);
		}
	}
	
	Color(char code) {
		this(code, false);
	}
	
	Color(char code, boolean isFormat) {
		this.code = code;
		this.isFormat = isFormat;
		toString = new String(new char[] {COLOR_CHAR, code});
		toStringSimple = new String(new char[] {'&', code});
	}
	
	/**
	 * Get the char of the color
	 *
	 * @return Return the char of the color
	 */
	public char getChar() {
		return code;
	}
	
	/**
	 * Is the a format code instead of color
	 *
	 * @return True if its a format code
	 */
	public boolean isFormat() {
		return isFormat;
	}
	
	@Override
	public String toString()
	{
		return toString;
	}
	
	/**
	 * Get the string value with the syntax &amp; and the color code
	 *
	 * @return The string value simplified
	 */
	public String toStringSimple()
	{
		return toStringSimple;
	}
	
	/**
	 * Get the color by its name
	 *
	 * @param name The name of the color
	 * @return The color found or null
	 */
	public static Color getByName(String name) {
		try {
			return Color.valueOf(CommonUtils.toUpperCase(name));
		} catch (Exception ignored) {}
		return null;
	}
	
	/**
	 * Get the color by its char
	 *
	 * @param code The char of the color
	 * @return The color found or null
	 */
	public static Color getByChar(char code) {
		return BY_CHAR.get(code);
	}
	
	/**
	 * Remove any color from the text
	 *
	 * @param input The text with color codes
	 * @return A new text without colors
	 */
	public static String stripColor(final String input) {
		return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll( "" );
	}
	
	/**
	 * Translate with alternative &amp; and remove any color from the text
	 *
	 * @param input The text with color codes
	 * @return A new text without colors
	 */
	public static String translateAndStripColor(final String input) {
		return input == null ? null : STRIP_COLOR_PATTERN.matcher(translateAlternateColorCodes(input)).replaceAll( "" );
	}
	
	/**
	 * Translate the string with &amp; as color char
	 *
	 * @param textToTranslate The text to translate
	 * @return The new text with translated colors
	 */
	public static String translateAlternateColorCodes(String textToTranslate) {
		return translateAlternateColorCodes('&', textToTranslate);
	}
	
	/**
	 * Translate the string with an alternative color char
	 *
	 * @param altColorChar The alternative char to use
	 * @param textToTranslate The text to translate
	 * @return The new text with translated colors
	 */
	public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		char[] chars = translateHex(altColorChar, textToTranslate).toCharArray();
		for (int index = 0; index < chars.length; index++) {
			if (chars[index] == altColorChar && ALL_CODES.indexOf(chars[index + 1]) > -1) {
				chars[index] = COLOR_CHAR;
				chars[index + 1] = Character.toLowerCase(chars[index + 1]);
			}
		}
		return new String(chars);
	}
	
	/**
	 * Translate the string with an alternative color char followed by an hex color code
	 * @param altColorChar The alternative char to use
	 * @param textToTranslate The text to translate
	 * @return The new text with translated colors
	 */
	public static String translateHex(char altColorChar, String textToTranslate) {
		String ret = textToTranslate;
		Matcher matcher = Pattern.compile("(?i)" + altColorChar + HEX_CHAR + "([0-9A-F]{6})").matcher(textToTranslate);
		while (matcher.find()) {
			StringBuilder hex = new StringBuilder();
			hex.append(Color.HEX);
			char[] chars = matcher.group(1).toCharArray();
			for (int index = 0; index < chars.length; index++) {
				hex.append(COLOR_CHAR).append(Character.toLowerCase(chars[index]));
			}
			ret = ret.replace(matcher.group(), hex.toString());
		}
		return ret;
	}
	
	/**
	 * Format the latest colors of the text
	 *
	 * @param text The text used to retrieve latest colors
	 * @return Colors found parsed
	 */
	public static String formatLastColors(String text) {
		StringBuilder ret = new StringBuilder();
		Pair<Color, Color> colors = getLastColors(text);
		if (colors.getKey() != null)
			ret.append(colors.getKey().toString());
		if (colors.getValue() != null)
			ret.append(colors.getValue().toString());
		return ret.toString();
	}
	
	/**
	 * Format the latest colors names of the text
	 *
	 * @param text The text used to retrieve latest colors
	 * @return Color names found
	 */
	public static String formatNamesLastColors(String text) {
		StringBuilder ret = new StringBuilder();
		Pair<Color, Color> colors = getLastColors(text);
		if (colors.getKey() != null)
			ret.append(colors.getKey().name());
		if (colors.getValue() != null)
			ret.append((colors.getKey() != null ? "+" : "")).append(colors.getValue().name());
		return ret.toString();
	}
	
	/**
	 * Format a color by getting it with its name
	 *
	 * @param name The color name to format
	 * @return The formatted value of the color or empty if doesn't exist
	 */
	public static String formatColorByName(String name) {
		return CommonUtils.ifNonNullReturn(getByName(name), Color::toString, "");
	}
	
	/**
	 * Format a color name by getting it with its name
	 *
	 * @param name The color name to format
	 * @return The original color name or empty if doesn't exist
	 */
	public static String formatColorNamesByName(String name) {
		return CommonUtils.ifNonNullReturn(getByName(name), Color::name, "");
	}
	
	/**
	 * Format a color by getting it with its name or color codes
	 *
	 * @param text The color name or color codes to format
	 * @return A string with parsed colors
	 */
	public static String formatColorByNameOrText(String text) {
		return CommonUtils.ifNonNullReturn(getByName(text), Color::toString, formatLastColors(text));
	}
	
	/**
	 * Format a color name by getting it with its name or color codes
	 *
	 * @param text The color name or color codes to format
	 * @return A string with color names
	 */
	public static String formatColorNamesByNameOrText(String text) {
		return CommonUtils.ifNonNullReturn(getByName(text), Color::name, formatNamesLastColors(text));
	}
	
	/**
	 * Get the latest colors text. A pair of Color and ColorFormat
	 *
	 * @param text The text used to retrieve latest colors
	 * @return A pair with the color found and the color style found
	 */
	public static Pair<Color, Color> getLastColors(String text) {
		Color color = null;
		Color format = null;
		int length = text.length();
		
		// Search backwards from the end as it is faster
		for (int index = length - 1; index > -1; index--) {
			char section = text.charAt(index);
			if (section == COLOR_CHAR && index < length - 1) {
				char c = text.charAt(index + 1);
				Color col = Color.getByChar(c);
				
				if (col != null) {
					if (col.isFormat && format == null)
						format = col;
					else {
						color = col;
						break;
					}
				}
			}
		}
		return new Pair<>(color, format);
	}
}
