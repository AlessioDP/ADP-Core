package com.alessiodp.core.common.commands;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import com.alessiodp.core.common.commands.utils.CommandData;
import com.alessiodp.core.common.commands.utils.CommandUtils;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public abstract class CommandManager {
	@NonNull protected final ADPPlugin plugin;
	
	@Getter protected List<ADPMainCommand> mainCommands;
	@Getter protected LinkedList<String> commandOrder;
	@Getter protected CommandUtils commandUtils;
	
	/**
	 * Setup commands
	 */
	public final void setup() {
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_PREPARE, true);
		prepareCommands();
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_REGISTER, true);
		registerCommands();
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_SETUP, true);
		setupCommands();
		plugin.getLoggerManager().logDebug(Constants.DEBUG_CMD_SETUP_ORDER, true);
		orderCommands();
	}
	
	/**
	 * Prepare commands: initialize enums and utils
	 */
	protected abstract void prepareCommands();
	
	/**
	 * Register all commands: add them into mainExecutors
	 */
	protected abstract void registerCommands();
	
	/**
	 * Setup commands and register them for the server
	 */
	private void setupCommands() {
		for (ADPMainCommand command : mainCommands) {
			CommandUtils.RegisterResult res = getCommandUtils().register(command);
			
			if (res == CommandUtils.RegisterResult.FAILED) {
				plugin.getLoggerManager().printError(Constants.DEBUG_CMD_SETUP_FAILED
						.replace("{command}", command.getCommandName()));
			} else if (res == CommandUtils.RegisterResult.OVERWRITTEN) {
				plugin.getLoggerManager().printError(Constants.DEBUG_CMD_SETUP_OVERWRITTEN
						.replace("{command}", command.getCommandName()));
			}
		}
	}
	
	/**
	 * Order commands
	 */
	private void orderCommands() {
		if (commandOrder != null) {
			LinkedList<ADPCommand> newEnabledSubCommands = new LinkedList<>();
			// Iterate command order list
			for (String command : commandOrder) {
				String[] splittedCommand = command.toLowerCase(Locale.ENGLISH).split(":");
				if (splittedCommand.length == 2) {
					// Iterate main commands
					for (ADPMainCommand mainCommand : mainCommands) {
						// Check only found main command
						if (splittedCommand[0].equalsIgnoreCase(mainCommand.getCommandName())) {
							// Interate every sub command
							for (ADPCommand subCommand : mainCommand.getEnabledSubCommands()) {
								// If the command match add it into the list
								if (subCommand.getOriginalName().equalsIgnoreCase(splittedCommand[1])) {
									newEnabledSubCommands.add(subCommand);
								}
							}
						}
					}
				}
			}
			
			// Add other commands at the end
			for (ADPMainCommand mainCommand : mainCommands) {
				for (ADPCommand subCommand : mainCommand.getEnabledSubCommands()) {
					if (!newEnabledSubCommands.contains(subCommand)) {
						newEnabledSubCommands.add(subCommand);
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
