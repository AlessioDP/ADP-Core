package com.alessiodp.core.bukkit.commands.utils;

import com.alessiodp.core.bukkit.user.BukkitUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import com.alessiodp.core.common.utils.CommonUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitCommandImpl extends Command implements CommandExecutor {
	private final ADPPlugin plugin;
	@Setter @Getter private ADPMainCommand mainCommand;
	
	public BukkitCommandImpl(ADPPlugin plugin, @NonNull ADPMainCommand mainCommand) {
		super(mainCommand.getCommandName());
		this.plugin = plugin;
		this.mainCommand = mainCommand;
		super.description = CommonUtils.getOr(mainCommand.getDescription(), "");
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String cmd, String[] args) {
		return this.onCommand(commandSender,this, cmd, args);
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String cmd, String[] args) {
		return mainCommand.onCommand(new BukkitUser(plugin, commandSender), cmd, args);
	}
	
	@Override
	public List<String> tabComplete(CommandSender commandSender, String alias, String[] args) {
		return mainCommand.onTabComplete(new BukkitUser(plugin, commandSender), args);
	}
}
