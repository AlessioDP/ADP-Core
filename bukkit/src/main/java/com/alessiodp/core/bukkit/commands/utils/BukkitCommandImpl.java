package com.alessiodp.core.bukkit.commands.utils;

import com.alessiodp.core.bukkit.user.BukkitUser;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitCommandImpl extends Command implements CommandExecutor {
	@Setter @Getter private ADPMainCommand mainCommand;
	
	public BukkitCommandImpl(@NonNull ADPMainCommand mainCommand) {
		super(mainCommand.getCommandName());
		this.mainCommand = mainCommand;
		super.description = mainCommand.getDescription();
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String cmd, String[] args) {
		return this.onCommand(commandSender,this, cmd, args);
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String cmd, String[] args) {
		return mainCommand.onCommand(new BukkitUser(commandSender), cmd, args);
	}
	
	@Override
	public List<String> tabComplete(CommandSender commandSender, String alias, String[] args) {
		return mainCommand.onTabComplete(new BukkitUser(commandSender), args);
	}
}
