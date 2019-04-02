package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class ADPSubCommand {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final ADPMainCommand mainCommand;
	
	/**
	 * Is the command executable by console
	 *
	 * @return true if is executable
	 */
	public abstract boolean isExecutableByConsole();
	
	/**
	 * Used to check player before async execution of the command handler.
	 *
	 * @param commandData the command payload
	 * @return true if the player can execute the command
	 */
	public abstract boolean preRequisites(@NonNull CommandData commandData);
	
	/**
	 * Async command handler
	 *
	 * @param commandData the command payload
	 */
	public abstract void onCommand(@NonNull CommandData commandData);
	
	/**
	 * Used to tab complete the command
	 *
	 * @param sender the user that send the tab complete
	 * @param args arguments of the tab complete
	 * @return a list of strings to tab complete
	 */
	public List<String> onTabComplete(@NonNull User sender, String[] args){
		return new ArrayList<>();
	}
}