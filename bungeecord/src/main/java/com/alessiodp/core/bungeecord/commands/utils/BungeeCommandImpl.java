package com.alessiodp.core.bungeecord.commands.utils;

import com.alessiodp.core.bungeecord.user.BungeeUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommandImpl extends Command implements TabExecutor {
	private final ADPPlugin plugin;
	@Setter @Getter private ADPMainCommand mainCommand;
	
	public BungeeCommandImpl(@NonNull ADPPlugin plugin, @NonNull ADPMainCommand mainCommand) {
		super(mainCommand.getCommandName(), null, mainCommand.getAliases().toArray(new String[0]));
		this.plugin = plugin;
		this.mainCommand = mainCommand;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		mainCommand.onCommand(new BungeeUser(plugin, commandSender), this.getName(), args);
	}
	
	@Override
	public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
		return mainCommand.onTabComplete(new BungeeUser(plugin, commandSender), args);
	}
}
