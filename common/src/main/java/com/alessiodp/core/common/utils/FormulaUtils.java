package com.alessiodp.core.common.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class FormulaUtils {
	private static ScriptEngine scriptEngine;
	
	private static void initializeEngine() {
		scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		if (scriptEngine == null)
			scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		if (scriptEngine == null)
			throw new RuntimeException("Failed to load script engine");
	}
	
	public static String calculate(String formula) throws ScriptException {
		if (scriptEngine == null)
			initializeEngine();
		return scriptEngine
				.eval(formula)
				.toString();
	}
}
