package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public abstract class ADPMainCommand implements ADPExecutableCommand {
	@NonNull protected final ADPPlugin plugin;
	
	@Getter @NonNull protected final ADPCommand command;
	@Getter @NonNull protected final String commandName;
	@Getter protected final boolean executableByConsole;
	@Getter protected boolean listedInHelp = false;
	
	@Getter protected String description = "";
	
	@Getter protected String help = "";
	
	@Getter protected String syntax = "";
	
	@Getter protected String runCommand = "";
	
	/**
	 * List of sub commands
	 */
	protected HashMap<String, ADPSubCommand> subCommands;
	@Getter protected HashMap<ADPCommand, ADPSubCommand> subCommandsByEnum;
	
	/**
	 * Enable tab support for this command
	 */
	protected boolean tabSupport;
	
	/**
	 * Handle the command
	 *
	 * @param sender the user that sent the command
	 * @param command the command performed
	 * @param args arguments of the command
	 * @return true if has been handled
	 */
	public abstract boolean onCommand(@NonNull User sender, String command, String[] args);
	
	/**
	 * Handle tab complete event
	 *
	 * @param sender the user that send the tab complete
	 * @param args arguments of the tab complete
	 * @return a list of strings to tab complete
	 */
	public List<String> onTabComplete(@NonNull User sender, String[] args) {
		List<String> ret = new ArrayList<>();
		if (tabSupport) {
			List<String> commands = new ArrayList<>();
			for (ADPCommand pc : plugin.getPlayerUtils().getAllowedCommands(sender)) {
				ADPSubCommand sc = subCommandsByEnum.get(pc);
				if (sc != null)
					commands.add(CommonUtils.toLowerCase(sc.getCommandName()));
			}
			
			if (args.length > 1) {
				if (commands.contains(args[0])) {
					ret = getSubCommand(args[0]).onTabComplete(sender, args);
				}
			} else {
				ret = plugin.getCommandManager().getCommandUtils().tabCompleteParser(commands, args[0]);
			}
		}
		return ret;
	}
	
	/**
	 * Register the sub command into the list
	 *
	 * @param subCommand the sub command to register
	 */
	public final void register(@NonNull ADPSubCommand subCommand) {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_REGISTER_SUBCOMMAND
				.replace("{sub}", CommonUtils.toLowerCase(subCommand.getCommandName()))
				.replace("{main}", CommonUtils.toLowerCase(getCommandName())), true);
		subCommands.put(CommonUtils.toLowerCase(subCommand.getCommandName()), subCommand);
		subCommandsByEnum.put(subCommand.getCommand(), subCommand);
	}
	
	/**
	 * Does the given command exist?
	 *
	 * @param command the command name
	 * @return true if the command exists
	 */
	public final boolean exists(String command) {
		return subCommands.containsKey(command);
	}
	
	/**
	 * Get sub command with given name
	 *
	 * @param command the command name
	 * @return the sub command found
	 */
	protected final ADPSubCommand getSubCommand(String command) {
		return subCommands.get(command);
	}
}