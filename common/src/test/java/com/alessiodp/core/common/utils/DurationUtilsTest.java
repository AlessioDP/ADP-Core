package com.alessiodp.core.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DurationUtils.class)
public class DurationUtilsTest {
	
	@Test
	public void testFormatSeconds() {
		String format = "s's'";
		assertEquals(DurationUtils.format(100, format), "100s");
		
		format = "m'm' s's'";
		assertEquals(DurationUtils.format(60 + 40, format), "1m 40s");
		
		format = "H'h' m'm' s's'";
		assertEquals(DurationUtils.format(60 + 40, format), "0h 1m 40s");
		
		format = "H'h' m'm' s's'";
		assertEquals(DurationUtils.format(3600 + 60 + 40, format), "1h 1m 40s");
		
		format = "d'd 'H'h' m'm' s's'";
		assertEquals(DurationUtils.format((86400 * 2) + 3600 + 120 + 20, format), "2d 1h 2m 20s");
	}
	
	@Test
	public void testFormatMilliseconds() {
		String format = "H'h' m'm' s's' S";
		assertEquals(DurationUtils.formatMilliseconds(((3600 + 60 + 40) * 1000L) + 50, format), "1h 1m 40s 50");
	}
	
	@Test
	public void testFormatWith() {
		assertEquals(DurationUtils.formatWith(100, "'seconds'"), "seconds");
		
		assertEquals(DurationUtils.formatWith(100, "'minutes'", "'seconds'"), "minutes");
		
		assertEquals(DurationUtils.formatWith(5000, "'hours'", "'minutes'", "'seconds'"), "hours");
		
		assertEquals(DurationUtils.formatWith(100000, "'days'", "'hours'", "'minutes'", "'seconds'"), "days");
		
		assertEquals(DurationUtils.formatWith(10000000, "'hours'", "'minutes'", "'seconds'"), "hours");
	}
}
