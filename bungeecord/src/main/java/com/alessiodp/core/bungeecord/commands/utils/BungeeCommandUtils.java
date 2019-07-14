package com.alessiodp.core.bungeecord.commands.utils;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.commands.utils.ADPMainCommand;
import com.alessiodp.core.common.commands.utils.CommandUtils;
import lombok.NonNull;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCommandUtils extends CommandUtils {
	
	public BungeeCommandUtils(@NonNull ADPPlugin plugin, String commandOn, String commandOff) {
		super(plugin, commandOn, commandOff);
	}
	
	@Override
	public RegisterResult register(@NonNull ADPMainCommand command) {
		RegisterResult ret = RegisterResult.FAILED;
		try {
			Plugin bungeePlugin = (Plugin) plugin.getBootstrap();
			BungeeCommandImpl bungeeCommandImplementation = new BungeeCommandImpl(plugin, command);
			
			bungeePlugin.getProxy().getPluginManager().registerCommand(bungeePlugin, bungeeCommandImplementation);
			ret = RegisterResult.SUCCESSFUL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
