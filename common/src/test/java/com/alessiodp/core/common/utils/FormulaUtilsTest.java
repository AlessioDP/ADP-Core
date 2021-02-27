package com.alessiodp.core.common.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.script.*", "com.sun.script.*"})
public class FormulaUtilsTest {
	private boolean runnable = false;
	
	@Before
	public void setUp() {
		// Java 15 we use JDK Nashorn module but cannot be loaded in earlier versions
		int version = Integer.parseInt(System.getProperties().getProperty("java.version").split("\\.")[0]);
		if (version < 15)
			runnable = true;
	}
	
	@Test
	public void testSumOperation() throws ScriptException {
		if (runnable)
			assertEquals("2", FormulaUtils.calculate("1+1"));
	}
	
	@Test
	public void testMultOperation() throws ScriptException {
		if (runnable)
			assertEquals("4", FormulaUtils.calculate("2*2"));
	}
	
	@Test
	public void testDivOperation() throws ScriptException {
		if (runnable)
			assertEquals("3", FormulaUtils.calculate("6/2"));
	}
	
	@Test
	public void testDivOperationFloating() throws ScriptException {
		if (runnable) {
			assertEquals("2.5", FormulaUtils.calculate("5/2"));
			assertEquals(2.5D, Double.parseDouble(FormulaUtils.calculate("5/2")), 0);
			assertEquals(2502.5D, Double.parseDouble(FormulaUtils.calculate("5005/2")), 0);
		}
	}
	
	@Test
	public void testLogOperation() throws ScriptException {
		if (runnable)
			assertEquals("0.0", FormulaUtils.calculate("Math.log(1)"));
	}
}
