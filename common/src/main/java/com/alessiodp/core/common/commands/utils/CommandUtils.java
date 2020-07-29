package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class CommandUtils {
	@NonNull protected final ADPPlugin plugin;
	private String commandOn;
	private String commandOff;
	
	/**
	 * Register the command
	 *
	 * @param command the command to register
	 * @return the register result
	 */
	public abstract RegisterResult register(@NonNull ADPMainCommand command);
	
	/**
	 * Trigger the command handler
	 *
	 * @param sender the user that is executing the command
	 * @param mainCommand the command performed
	 * @param command the sub command performed
	 * @param args arguments of the command
	 */
	public void executeCommand(@NonNull User sender, String mainCommand, @NonNull ADPSubCommand command, String[] args) {
		CommandData cd = plugin.getCommandManager().initializeCommandData();
		cd.setSender(sender);
		cd.setCommandLabel(mainCommand);
		cd.setArgs(args);
		if (command.preRequisites(cd)) {
			plugin.getScheduler().runAsync(() -> command.onCommand(cd));
		}
	}
	
	/**
	 * Handle on/off commands
	 *
	 * @param value the default value
	 * @param args the command arguments
	 * @return true if the result is on
	 */
	public Boolean handleOnOffCommand(Boolean value, String[] args) {
		Boolean ret = value;
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase(commandOn))
				ret = true;
			else if (args[1].equalsIgnoreCase(commandOff))
				ret = false;
			else
				ret = null;
		} else {
			ret = !ret;
		}
		return ret;
	}
	
	/**
	 * Handle string into a command, get the entire string since start to end of arguments
	 *
	 * @param args the command arguments
	 * @param start the start of the string
	 * @return the string
	 */
	public String handleCommandString(String[] args, int start) {
		StringBuilder sb = new StringBuilder();
		for (int word = start; word < args.length; word++) {
			if (sb.length() > 0)
				sb.append(" ");
			sb.append(args[word]);
		}
		return sb.toString();
	}
	
	/**
	 * Parse tab complete command list
	 *
	 * @param commands the list of commands
	 * @param word the word to check
	 * @return a list of commands that starts with word
	 */
	public List<String> tabCompleteParser(List<String> commands, String word) {
		List<String> ret = new ArrayList<>();
		for (String s : commands) {
			if (CommonUtils.toLowerCase(s).startsWith(CommonUtils.toLowerCase(word))) {
				ret.add(s);
			}
		}
		return ret;
	}
	
	/**
	 * Handle on/off tab complete
	 *
	 * @param args the command arguments (on/off)
	 * @return a list of on/off that match args
	 */
	public List<String> tabCompleteOnOff(String[] args) {
		List<String> ret = new ArrayList<>();
		if (args.length == 2) {
			ret.add(commandOn);
			ret.add(commandOff);
			if (!args[1].isEmpty()) {
				ret = tabCompleteParser(ret, args[1]);
			}
		}
		return ret;
	}
	
	/**
	 * Insert player list inside tab complete
	 *
	 * @param args the command arguments
	 * @param index the argument index
	 * @param bypassVanish show vanished players
	 * @return a list of players that match args
	 */
	public List<String> tabCompletePlayerList(String[] args, int index, boolean bypassVanish) {
		// args: <cmd> ... playerListAtIndex ...
		List<String> ret = new ArrayList<>();
		if (args.length == (index + 1)) {
			for (User u : plugin.getOnlinePlayers()) {
				if (bypassVanish || !u.isVanished()) {
					ret.add(u.getName());
				}
			}
			ret = tabCompleteParser(ret, args[index]);
		}
		return ret;
	}
	
	/**
	 * Insert player list inside tab complete
	 *
	 * @param args the command arguments
	 * @param index the argument index
	 * @return a list of players that match args
	 */
	public List<String> tabCompletePlayerList(String[] args, int index) {
		return tabCompletePlayerList(args, index, false);
	}
	
	/**
	 * Result of register method
	 */
	public enum RegisterResult {
		SUCCESSFUL, OVERWRITTEN, FAILED
	}
}
