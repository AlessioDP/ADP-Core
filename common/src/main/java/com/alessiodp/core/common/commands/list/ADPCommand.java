package com.alessiodp.core.common.commands.list;

public interface ADPCommand {
	/**
	 * Get command name
	 *
	 * @return the command name
	 */
	String getCommand();
	
	/**
	 * Get help message
	 *
	 * @return the help message
	 */
	String getHelp();
	
	/**
	 * Get original name of the command
	 *
	 * @return the original command name
	 */
	String getOriginalName();
	
	/**
	 * Get permission of the command
	 *
	 * @return the permission
	 */
	String getPermission();
}