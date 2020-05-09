package com.alessiodp.core.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Color.class)
public class ColorTest {
	
	@Test
	public void toStringWorks() {
		for (Color color : Color.values()) {
			assertEquals(String.format("%c%c", Color.COLOR_CHAR, color.getChar()), color.toString());
		}
	}
	
	@Test
	public void toStringSimpleWorks() {
		for (Color color : Color.values()) {
			assertEquals(String.format("&%c", color.getChar()), color.toStringSimple());
		}
	}
	
	@Test
	public void getByName() {
		for (Color color : Color.values()) {
			assertEquals(Color.getByName(color.name().toLowerCase(Locale.ENGLISH)), color);
		}
	}
	
	@Test
	public void getByChar() {
		for (Color color : Color.values()) {
			assertEquals(Color.getByChar(color.getChar()), color);
		}
	}
	
	@Test
	public void stripColor() {
		StringBuilder subject = new StringBuilder();
		StringBuilder expected = new StringBuilder();
		
		final String filler = "test";
		for (Color color : Color.values()) {
			subject.append(color).append(filler);
			expected.append(filler);
		}
		
		assertEquals(Color.stripColor(subject.toString()), expected.toString());
	}
	
	@Test
	public void translateAndStripColor() {
		StringBuilder subject = new StringBuilder();
		StringBuilder expected = new StringBuilder();
		
		final String filler = "test";
		for (Color color : Color.values()) {
			subject.append(color.toStringSimple()).append(filler);
			expected.append(filler);
		}
		
		assertEquals(Color.translateAndStripColor(subject.toString()), expected.toString());
	}
	
	@Test
	public void translateAlternateColorCodes() {
		String s = "&0&1&2&3&4&5&6&7&8&9&A&a&B&b&C&c&D&d&E&e&F&f&K&k & more";
		String t = Color.translateAlternateColorCodes(s);
		String u = Color.BLACK.toString() + Color.DARK_BLUE + Color.DARK_GREEN + Color.DARK_AQUA + Color.DARK_RED + Color.DARK_PURPLE + Color.GOLD + Color.GRAY + Color.DARK_GRAY + Color.BLUE + Color.GREEN + Color.GREEN + Color.AQUA + Color.AQUA + Color.RED + Color.RED + Color.LIGHT_PURPLE + Color.LIGHT_PURPLE + Color.YELLOW + Color.YELLOW + Color.WHITE + Color.WHITE + Color.MAGIC + Color.MAGIC + " & more";
		assertEquals(t, u);
	}
	
	@Test
	public void formatLastColors() {
		String s = String.format("%c%ctest%c%ctest%c", Color.COLOR_CHAR, Color.RED.getChar(), Color.COLOR_CHAR, Color.ITALIC.getChar(), Color.COLOR_CHAR);
		String expected = Color.RED.toString() + Color.ITALIC;
		assertEquals(Color.formatLastColors(s), expected);
		
		s = String.format("%c%ctest%c%ctest", Color.COLOR_CHAR, Color.RED.getChar(), Color.COLOR_CHAR, Color.BLUE.getChar());
		assertEquals(Color.formatLastColors(s), Color.BLUE.toString());
	}
	
	@Test
	public void formatNamesLastColors() {
		String s = String.format("%c%ctest%c%ctest%c", Color.COLOR_CHAR, Color.RED.getChar(), Color.COLOR_CHAR, Color.ITALIC.getChar(), Color.COLOR_CHAR);
		String expected = Color.RED.name() + "+" + Color.ITALIC.name();
		assertEquals(Color.formatNamesLastColors(s), expected);
		
		s = String.format("%c%ctest%c%ctest", Color.COLOR_CHAR, Color.RED.getChar(), Color.COLOR_CHAR, Color.BLUE.getChar());
		assertEquals(Color.formatNamesLastColors(s), Color.BLUE.name());
	}
	
	@Test
	public void formatColorByName() {
		String s = "red";
		String expected = Color.RED.toString();
		assertEquals(Color.formatColorByName(s), expected);
		
		assertEquals(Color.formatColorByName("unknown"), "");
	}
	
	@Test
	public void formatColorNamesByName() {
		String s = "red";
		String expected = Color.RED.name();
		assertEquals(Color.formatColorNamesByName(s), expected);
		
		assertEquals(Color.formatColorByName("unknown"), "");
	}
	
	@Test
	public void formatColorByNameOrText() {
		String s = "red";
		String expected = Color.RED.toString();
		assertEquals(Color.formatColorByNameOrText(s), expected);
		
		s = Color.RED.toString() + Color.BLUE + Color.ITALIC;
		expected = Color.BLUE.toString() + Color.ITALIC;
		assertEquals(Color.formatColorByNameOrText(s), expected);
	}
	
	@Test
	public void formatColorNamesByNameOrText() {
		String s = "red";
		String expected = Color.RED.name();
		assertEquals(Color.formatColorNamesByNameOrText(s), expected);
		
		s = Color.RED.toString() + Color.BLUE.toString() + Color.ITALIC.toString();
		expected = Color.BLUE.name() + "+" + Color.ITALIC.name();
		assertEquals(Color.formatColorNamesByNameOrText(s), expected);
	}
	
	@Test
	public void getLastColors() {
		String s = "&ate&cst&mer &z";
		Pair<Color, Color> expected = new Pair<>(Color.RED, Color.STRIKETHROUGH);
		assertEquals(Color.getLastColors(Color.translateAlternateColorCodes(s)), expected);
		
		s = "&ate&cster &z";
		expected = new Pair<>(Color.RED, null);
		assertEquals(Color.getLastColors(Color.translateAlternateColorCodes(s)), expected);
		
		s = "test&mer &z";
		expected = new Pair<>(null, Color.STRIKETHROUGH);
		assertEquals(Color.getLastColors(Color.translateAlternateColorCodes(s)), expected);
		
		s = "tester &z";
		expected = new Pair<>(null, null);
		assertEquals(Color.getLastColors(Color.translateAlternateColorCodes(s)), expected);
	}
}
