package com.alessiodp.core.common.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommonUtils {
	private static DecimalFormat decimalFormat;
	
	
	public static <T> T getOr(T value, T def) {
		return value != null ? value : def;
	}
	
	public static String getNoEmptyOr(String value, String def) {
		return (value != null && !value.isEmpty()) ? value : def;
	}
	
	public static <T> void ifNonNullDo(T value, Consumer<T> consumer) {
		if (value != null) consumer.accept(value);
	}
	
	public static <T, R> R ifNonNullReturn(T value, Function<T, R> fun, R def) {
		if (value != null)
			return fun.apply(value);
		return def;
	}
	
	public static void ifNonEmptyDo(String value, Consumer<String> consumer) {
		if (value != null && !value.isEmpty()) consumer.accept(value);
	}
	
	public static void ifNonEmptyDo(String value, Runnable consumer) {
		if (value != null && !value.isEmpty()) consumer.run();
	}
	
	public static String toUpperCase(String str) {
		return str.toUpperCase(Locale.ENGLISH);
	}
	
	public static String toLowerCase(String str) {
		return str.toLowerCase(Locale.ENGLISH);
	}
	
	public static String formatInteger(int number) {
		return getDecimalFormat().format(number);
	}
	
	public static String formatDouble(double number) {
		return getDecimalFormat().format(number);
	}
	
	private static DecimalFormat getDecimalFormat() {
		if (decimalFormat == null) {
			decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator(',');
			decimalFormat.setDecimalFormatSymbols(dfs);
		}
		return decimalFormat;
	}
}
