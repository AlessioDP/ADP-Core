package com.alessiodp.core.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

public class DurationUtils {
	
	public static String formatWith(long duration, String formatSeconds) {
		return formatWith(duration, formatSeconds, formatSeconds, formatSeconds, formatSeconds);
	}
	
	public static String formatWith(long duration, String formatMinutes, String formatSeconds) {
		return formatWith(duration, formatMinutes, formatMinutes, formatMinutes, formatSeconds);
	}
	
	public static String formatWith(long duration, String formatHours, String formatMinutes, String formatSeconds) {
		return formatWith(duration, formatHours, formatHours, formatMinutes, formatSeconds);
	}
	
	public static String formatWith(long duration, String formatDays, String formatHours, String formatMinutes, String formatSeconds) {
		if (duration >= 86400)
			return format(duration, formatDays);
		else if (duration >= 3600)
			return format(duration, formatHours);
		else if (duration >= 60)
			return format(duration, formatMinutes);
		return format(duration, formatSeconds);
	}
	
	public static String formatMilliseconds(long durationMillis, String format) {
		ArrayList<Token> tokens = parse(format);
		long remainingDuration = durationMillis;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		int milliseconds = 0;
		if (Token.containsTokenWithValue(tokens, Tokens.DAYS)) {
			days = (int)(remainingDuration / 86400000L);
			remainingDuration -= (long)days * 86400000L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.HOURS)) {
			hours = (int)(remainingDuration / 3600000L);
			remainingDuration -= (long)hours * 3600000L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.MINUTES)) {
			minutes = (int)(remainingDuration / 60000L);
			remainingDuration -= (long)minutes * 60000L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.SECONDS)) {
			seconds = (int)(remainingDuration / 1000L);
			remainingDuration -= (long)seconds * 1000L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.MILLISECONDS)) {
			milliseconds = (int)remainingDuration;
		}
		
		return format(tokens, 0, 0, days, hours, minutes, seconds, milliseconds);
	}
	
	public static String format(long durationSeconds, String format) {
		ArrayList<Token> tokens = parse(format);
		long remainingDuration = durationSeconds;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		if (Token.containsTokenWithValue(tokens, Tokens.DAYS)) {
			days = (int)(remainingDuration / 86400L);
			remainingDuration -= (long)days * 86400L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.HOURS)) {
			hours = (int)(remainingDuration / 3600L);
			remainingDuration -= (long)hours * 3600L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.MINUTES)) {
			minutes = (int)(remainingDuration / 60L);
			remainingDuration -= (long)minutes * 60L;
		}
		
		if (Token.containsTokenWithValue(tokens, Tokens.SECONDS)) {
			seconds = (int)remainingDuration;
		}
		
		return format(tokens, 0, 0, days, hours, minutes, seconds, 0);
	}
	
	private static String format(ArrayList<Token> tokens, int years, int months, int days, int hours, int minutes, int seconds, int milliseconds) {
		StringBuilder buffer = new StringBuilder();
		
		for (Token token : tokens) {
			if (token.getValue() instanceof StringBuffer) {
				buffer.append(token.getValue());
			} else if (token.getValue() == Tokens.YEARS) {
				buffer.append(years);
			} else if (token.getValue() == Tokens.MONTHS) {
				buffer.append(months);
			} else if (token.getValue() == Tokens.DAYS) {
				buffer.append(days);
			} else if (token.getValue() == Tokens.HOURS) {
				buffer.append(hours);
			} else if (token.getValue() == Tokens.MINUTES) {
				buffer.append(minutes);
			} else if (token.getValue() == Tokens.SECONDS) {
				buffer.append(seconds);
			} else if (token.getValue() == Tokens.MILLISECONDS) {
				buffer.append(milliseconds);
			}
		}
		return buffer.toString();
	}
	
	private static ArrayList<Token> parse(String format) {
		char[] array = format.toCharArray();
		ArrayList<Token> ret = new ArrayList<>(array.length);
		
		boolean inLiteral = false;
		StringBuffer buffer = null;
		
		for (char ch : array) {
			if (inLiteral && ch != '\'') {
				buffer.append(ch);
			} else {
				Object value = null;
				switch (ch) {
					case '\'':
						if (inLiteral) {
							buffer = null;
							inLiteral = false;
						} else {
							buffer = new StringBuffer();
							ret.add(new Token(buffer));
							inLiteral = true;
						}
						break;
					case 'H':
						value = Tokens.HOURS;
						break;
					case 'M':
						value = Tokens.MONTHS;
						break;
					case 'S':
						value = Tokens.MILLISECONDS;
						break;
					case 'd':
						value = Tokens.DAYS;
						break;
					case 'm':
						value = Tokens.MINUTES;
						break;
					case 's':
						value = Tokens.SECONDS;
						break;
					case 'y':
						value = Tokens.YEARS;
						break;
					default:
						if (buffer == null) {
							buffer = new StringBuffer();
							ret.add(new Token(buffer));
						}
						
						buffer.append(ch);
						break;
				}
				
				if (value != null) {
					ret.add(new Token(value));
					
					buffer = null;
				}
			}
		}
		
		return ret;
	}
	
	@RequiredArgsConstructor
	enum Tokens {
		YEARS("y"), MONTHS("M"), DAYS("d"), HOURS("H"), MINUTES("m"), SECONDS("s"), MILLISECONDS("S");
		
		@Getter private final String code;
	}
	
	@AllArgsConstructor
	private static class Token {
		@Getter private final Object value;
		
		private static boolean containsTokenWithValue(ArrayList<Token> tokens, Object value) {
			for (Token token : tokens) {
				if (token.getValue() == value)
					return true;
			}
			return false;
		}
	}
}
