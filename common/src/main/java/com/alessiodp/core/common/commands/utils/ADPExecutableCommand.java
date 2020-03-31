package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.commands.list.ADPCommand;

public interface ADPExecutableCommand {
	/**
	 * Command enum
	 *
	 * @return the command enum
	 */
	ADPCommand getCommand();
	
	/**
	 * Name of this command
	 *
	 * @return the command name
	 */
	String getCommandName();
	
	/**
	 * Is this command listed in help page
	 *
	 * @return true if this command is listed in help page
	 */
	boolean isListedInHelp();
	
	/**
	 * Get the command description
	 *
	 * @return the command description
	 */
	String getDescription();
	
	/**
	 * Get the command help
	 *
	 * @return the command help
	 */
	String getHelp();
	
	/**
	 * Get the command syntax
	 *
	 * @return the command syntax
	 */
	String getSyntax();
	
	/**
	 * Get the console command syntax
	 *
	 * @return syntax of the command run in console
	 */
	default String getConsoleSyntax() {
		return getSyntax();
	}
	
	/**
	 * Get the run command command syntax
	 *
	 * @return the run command syntax
	 */
	String getRunCommand();
	
	/**
	 * Is the command executable by console
	 *
	 * @return true if is executable
	 */
	boolean isExecutableByConsole();
}
