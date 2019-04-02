package com.alessiodp.core.bukkit.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import com.alessiodp.core.common.commands.utils.CommandUtils;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

import java.lang.reflect.Field;

public class BukkitCommandUtils extends CommandUtils {
	
	public BukkitCommandUtils(@NonNull ADPPlugin plugin, String commandOn, String commandOff) {
		super(plugin, commandOn, commandOff);
	}
	
	@Override
	public RegisterResult register(@NonNull ADPMainCommand command) {
		RegisterResult ret = RegisterResult.FAILED;
		try {
			BukkitCommandImpl bukkitCommandImplementation = new BukkitCommandImpl(command);
			
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			
			Command bukkitCommand = commandMap.getCommand(bukkitCommandImplementation.getName());
			if (bukkitCommand == null) {
				// Unregistered command
				commandMap.register(plugin.getPluginFallbackName(), bukkitCommandImplementation);
				ret = RegisterResult.SUCCESSFUL;
			} else {
				// Already registered command
				bukkitCommand.setDescription(bukkitCommandImplementation.getDescription());
				
				if (bukkitCommand instanceof BukkitCommandImpl) {
					// ADP command - update it
					((BukkitCommandImpl) bukkitCommand).setMainCommand(bukkitCommandImplementation.getMainCommand());
					ret = RegisterResult.SUCCESSFUL;
				} else {
					// Unknown command - overwrite it
					if (bukkitCommand instanceof PluginCommand) {
						((PluginCommand) bukkitCommand).setExecutor((CommandExecutor) bukkitCommandImplementation.getMainCommand());
						ret = RegisterResult.OVERWRITTEN;
					}
				}
			}
			bukkitCommandMap.setAccessible(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}
