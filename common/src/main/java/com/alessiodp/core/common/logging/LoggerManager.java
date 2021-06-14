package com.alessiodp.core.common.logging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.bootstrap.ADPBootstrap;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.NonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class LoggerManager {
	private final ADPPlugin plugin;
	private final ReentrantLock lock = new ReentrantLock();
	
	// General
	@Getter private boolean debugEnabled;
	
	// Save to file
	private boolean saveToFile;
	private Path filePath;
	private String logFormat;
	
	public LoggerManager(@NonNull ADPPlugin instance) {
		plugin = instance;
		debugEnabled = false;
		saveToFile = false;
	}
	
	/**
	 * Reload logger manager
	 *
	 * @param debugEnabled toggle debug log
	 * @param saveToFile toggle save to file
	 * @param file file name
	 * @param logFormat format of the log output
	 */
	public void reload(boolean debugEnabled, boolean saveToFile, String file, String logFormat) {
		this.debugEnabled = debugEnabled;
		this.saveToFile = saveToFile;
		this.logFormat = logFormat;
		try {
			this.filePath = plugin.getFolder().resolve(file);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.saveToFile = false;
		}
		logDebug(Constants.DEBUG_LOG_LOADED, true);
	}
	
	/**
	 * Send an error log on console and log storage
	 *
	 * @param message the message to save
	 */
	public void printError(String message) {
		plugin.logConsole(ConsoleColor.RED.getCode() + message + ConsoleColor.RESET.getCode(), ADPBootstrap.LogLevel.WARNING);
		log(message, false);
	}
	
	/**
	 * Send an error log on console and log storage with a stacktrace
	 *
	 * @param message the message to save
	 * @param ex the error to parse
	 */
	public void printErrorStacktrace(String message, Exception ex) {
		printError(formatErrorCallTrace(message, ex));
	}
	
	/**
	 * Send a log message
	 *
	 * @param message the message to save
	 * @param printConsole print the message on console
	 */
	public void log(String message, boolean printConsole) {
		if (printConsole) {
			// Print it on the console
			plugin.logConsole(message, ADPBootstrap.LogLevel.WARNING);
		}
		
		if (saveToFile) {
			saveLogToFile(message);
		}
	}
	
	/**
	 * Send a debug log message
	 *
	 * @param message the message to save
	 * @param printConsole print the message on console
	 */
	public void logDebug(String message, boolean printConsole) {
		if (debugEnabled)
			log(message, printConsole);
	}
	
	/**
	 * Save a log message into a file
	 *
	 * @param message the message to save
	 */
	private void saveLogToFile(String message) {
		Date calendar = Calendar.getInstance().getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar);
		String time = new SimpleDateFormat("HH:mm:ss").format(calendar);
		
		lock.lock();
		try {
			if (!Files.exists(filePath))
				Files.createFile(filePath);
			
			try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
				writer.write(logFormat
						.replace("%date%", date)
						.replace("%time%", time)
						.replace("%message%", message));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Format the error calltrace
	 *
	 * @param base the message base
	 * @param ex the error to parse
	 * @return the message parsed
	 */
	private String formatErrorCallTrace(String base, Exception ex) {
		StackTraceElement st = Thread.currentThread().getStackTrace()[3];
		String[] clss = st.getClassName().split("\\.");
		return String.format(base,
				clss[clss.length - 1], // Class
				st.getMethodName(), // Method
				st.getLineNumber(), // Line
				ex.getClass().getSimpleName(), // Type
				ex.getMessage() != null ? ex.getMessage() : ex.toString(), // Message
				Arrays.stream(ex.getStackTrace())
						.map(StackTraceElement::toString)
						.collect(Collectors.joining("\n")) // Stacktrace
		);
	}
}
