package com.alessiodp.core.common.utils;

import com.alessiodp.core.common.ADPPlugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class FormulaUtils {
	private static ScriptEngine scriptEngine;
	
	public static void initializeEngine() {
		scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		if (scriptEngine == null)
			scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		if (scriptEngine == null) {
			// Probably Java 15+ - Load nashorn via library
			if (ADPPlugin.getInstance() != null) // If not testing
				ADPPlugin.getInstance().getLibraryManager().setupLibrariesForScripting();
			
			try {
				Object nashornEngineFactory = Class.forName("jdk.nashorn.api.scripting.NashornScriptEngineFactory").newInstance();
				scriptEngine = (ScriptEngine) nashornEngineFactory.getClass().getDeclaredMethod("getScriptEngine").invoke(nashornEngineFactory);
			} catch (Exception ignored) {}
		}
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
