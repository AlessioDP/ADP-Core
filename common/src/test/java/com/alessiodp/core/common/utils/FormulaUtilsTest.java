package com.alessiodp.core.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.script.*", "com.sun.script.*"})
public class FormulaUtilsTest {
	
	@Test
	public void testSumOperation() throws ScriptException {
		assertEquals("2", FormulaUtils.calculate("1+1"));
	}
	
	@Test
	public void testMultOperation() throws ScriptException {
		assertEquals("4", FormulaUtils.calculate("2*2"));
	}
	
	@Test
	public void testDivOperation() throws ScriptException {
		assertEquals("3", FormulaUtils.calculate("6/2"));
	}
	
	@Test
	public void testDivOperationFloating() throws ScriptException {
		assertEquals("2.5", FormulaUtils.calculate("5/2"));
		assertEquals(2.5D, Double.parseDouble(FormulaUtils.calculate("5/2")), 0);
		assertEquals(2502.5D, Double.parseDouble(FormulaUtils.calculate("5005/2")), 0);
	}
	
	@Test
	public void testLogOperation() throws ScriptException {
		assertEquals("0.0", FormulaUtils.calculate("Math.log(1)"));
	}
}
