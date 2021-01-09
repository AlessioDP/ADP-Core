package com.alessiodp.core.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CommonUtilsTest {
	
	@Test
	public void testGetOrNonNull() {
		Assert.assertEquals(Integer.valueOf(1), CommonUtils.getOr(1, 2));
	}
	
	@Test
	public void testGetOrNull() {
		Assert.assertEquals(Integer.valueOf(2), CommonUtils.getOr(null, 2));
	}
	
	@Test
	public void testGetNoEmptyOrNonEmpty() {
		Assert.assertEquals("1", CommonUtils.getNoEmptyOr("1", "2"));
	}
	
	@Test
	public void testGetNoEmptyOrEmpty() {
		Assert.assertEquals("2", CommonUtils.getNoEmptyOr("", "2"));
		Assert.assertEquals("2", CommonUtils.getNoEmptyOr(null, "2"));
	}
	
	@Test
	public void testIfNonNullDo() {
		CommonUtils.ifNonNullDo(null, (t) -> Assert.fail());
	}
	
	@Test
	public void testIfNonEmptyDoConsumer() {
		CommonUtils.ifNonEmptyDo("", (t) -> Assert.fail());
		CommonUtils.ifNonEmptyDo(null, (t) -> Assert.fail());
	}
	
	@Test
	public void testIfNonEmptyDoRunnable() {
		CommonUtils.ifNonEmptyDo("", (Runnable) Assert::fail);
		CommonUtils.ifNonEmptyDo(null, (Runnable) Assert::fail);
	}
	
	@Test
	public void testToUpperCase() {
		Assert.assertEquals("1AB", CommonUtils.toUpperCase("1aB"));
		Assert.assertEquals("AABB CCDD", CommonUtils.toUpperCase("AaBb CcDd"));
	}
	
	@Test
	public void testToLowerCase() {
		Assert.assertEquals("1ab", CommonUtils.toLowerCase("1aB"));
		Assert.assertEquals("aabb ccdd", CommonUtils.toLowerCase("AaBb CcDd"));
	}
	
	@Test
	public void testFormatInteger() {
		Assert.assertEquals("0", CommonUtils.formatInteger(0));
		Assert.assertEquals("1", CommonUtils.formatInteger(1));
		Assert.assertEquals("1,234", CommonUtils.formatInteger(1234));
		Assert.assertEquals("1,234,567", CommonUtils.formatInteger(1234567));
	}
	
	@Test
	public void testFormatDouble() {
		Assert.assertEquals("0", CommonUtils.formatDouble(0D));
		Assert.assertEquals("1", CommonUtils.formatDouble(1D));
		Assert.assertEquals("1", CommonUtils.formatDouble(1.0D));
		Assert.assertEquals("1.123", CommonUtils.formatDouble(1.123D));
		Assert.assertEquals("1.123", CommonUtils.formatDouble(1.12300D));
		Assert.assertEquals("12,345", CommonUtils.formatDouble(12345.0D));
		Assert.assertEquals("12,345,678", CommonUtils.formatDouble(12345678.0D));
	}
}
