package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public abstract class ADPMainCommand {
	@NonNull protected final ADPPlugin plugin;
	
	/**
	 * Name of this command
	 */
	@Getter protected String commandName;
	
	/**
	 * Description of the command
	 */
	@Getter protected String description = "";
	
	/**
	 * List of sub commands
	 */
	protected HashMap<String, ADPSubCommand> subCommands;
	
	/**
	 * List of enabled sub commands
	 */
	@Getter protected List<ADPCommand> enabledSubCommands;
	
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
				if (enabledSubCommands.contains(pc))
					commands.add(pc.getCommand().toLowerCase(Locale.ENGLISH));
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
	 * @param command the main command
	 * @param subCommand the sub command to register
	 */
	protected final void register(@NonNull ADPCommand command, @NonNull ADPSubCommand subCommand) {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_REGISTER_SUBCOMMAND
				.replace("{sub}", command.getCommand().toLowerCase(Locale.ENGLISH))
				.replace("{main}", getCommandName().toLowerCase(Locale.ENGLISH)), true);
		subCommands.put(command.getCommand().toLowerCase(Locale.ENGLISH), subCommand);
		enabledSubCommands.add(command);
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