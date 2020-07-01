package com.alessiodp.core.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtils.class)
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
}
