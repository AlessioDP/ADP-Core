package com.alessiodp.core.common.commands;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.commands.utils.ADPExecutableCommand;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import com.alessiodp.core.common.commands.utils.CommandData;
import com.alessiodp.core.common.commands.utils.CommandUtils;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public abstract class CommandManager {
	@NonNull protected final ADPPlugin plugin;
	
	@Getter protected List<ADPMainCommand> mainCommands;
	@Getter protected LinkedHashMap<ADPCommand, ADPExecutableCommand> orderedCommands;
	@Getter protected CommandUtils commandUtils;
	protected LinkedList<String> commandOrder;
	
	/**
	 * Setup commands
	 */
	public final void setup() {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_PREPARE, true);
		prepareCommands();
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_REGISTER, true);
		registerCommands();
		setupCommands();
		orderCommands();
	}
	
	/**
	 * Prepare commands: initialize enums and utils
	 */
	public abstract void prepareCommands();
	
	/**
	 * Register all commands: add them into mainExecutors
	 */
	public abstract void registerCommands();
	
	/**
	 * Setup commands and register them for the server
	 */
	public void setupCommands() {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_SETUP, true);
		if (mainCommands != null) {
			for (ADPMainCommand command : mainCommands) {
				CommandUtils.RegisterResult res = getCommandUtils().register(command);
				
				if (res == CommandUtils.RegisterResult.FAILED) {
					plugin.getLoggerManager().printError(String.format(Constants.DEBUG_CMD_SETUP_FAILED, command.getCommandName()));
				} else if (res == CommandUtils.RegisterResult.OVERWRITTEN) {
					plugin.getLoggerManager().printError(String.format(Constants.DEBUG_CMD_SETUP_OVERWRITTEN, command.getCommandName()));
				}
			}
		}
	}
	
	/**
	 * Order commands
	 */
	public void orderCommands() {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_ORDER, true);
		if (mainCommands != null) {
			orderedCommands = new LinkedHashMap<>();
			if (commandOrder != null) {
				// Iterate command order list
				for (String command : commandOrder) {
					// Iterate main commands
					for (ADPMainCommand mainCommand : mainCommands) {
						if (mainCommand.getCommand().getOriginalName().equalsIgnoreCase(command)
								&& mainCommand.isListedInHelp()) {
							// Match with main command
							orderedCommands.put(mainCommand.getCommand(), mainCommand);
						} else {
							// Match with sub commands
							for (ADPCommand subCommand : mainCommand.getSubCommandsByEnum().keySet()) {
								if (subCommand.getOriginalName().equalsIgnoreCase(command)) {
									orderedCommands.put(subCommand, mainCommand.getSubCommandsByEnum().get(subCommand));
								}
							}
						}
					}
				}
			}
			
			// Add other commands at the end
			for (ADPMainCommand mainCommand : mainCommands) {
				if (!orderedCommands.containsKey(mainCommand.getCommand()) && mainCommand.isListedInHelp())
					orderedCommands.put(mainCommand.getCommand(), mainCommand);
				for (ADPCommand subCommand : mainCommand.getSubCommandsByEnum().keySet()) {
					if (!orderedCommands.containsKey(subCommand)) {
						orderedCommands.put(subCommand, mainCommand.getSubCommandsByEnum().get(subCommand));
					}
				}
			}
		}
	}
	
	/**
	 * Initialize a command data
	 *
	 * @return a new command data
	 */
	public abstract CommandData initializeCommandData();
}
