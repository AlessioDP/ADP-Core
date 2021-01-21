package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class ADPSubCommand implements ADPExecutableCommand {
	@NonNull protected final ADPPlugin plugin;
	@NonNull protected final ADPMainCommand mainCommand;
	@NonNull @Getter protected final ADPCommand command;
	@Getter protected final ADPPermission permission;
	@NonNull @Getter protected final String commandName;
	@Getter protected final boolean executableByConsole;
	@Getter protected boolean listedInHelp = true;
	
	@Getter protected String description;
	@Getter protected String help;
	@Getter protected String syntax;
	
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
	
	/**
	 * Used to make a base of the syntax
	 *
	 * @return the command syntax
	 */
	protected String baseSyntax() {
		return mainCommand.getCommandName() + " " + getCommandName();
	}
	
	/**
	 * Get the run command syntax
	 *
	 * @return the run command syntax
	 */
	public String getRunCommand() {
		return baseSyntax();
	}
}