package com.alessiodp.core.bukkit.user;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitUser implements User {
	private final CommandSender sender;
	private final JsonHandler jsonHandler;
	
	public BukkitUser(@NonNull CommandSender commandSender) {
		sender = commandSender;
		if (isSpigot())
			jsonHandler = new SpigotJsonHandler();
		else
			jsonHandler = new BukkitJsonHandler();
	}
	
	// Used to check if is running Spigot
	private boolean isSpigot() {
		boolean ret = false;
		try {
			Class.forName("net.md_5.bungee.chat.ComponentSerializer");
			ret = true;
		} catch (ClassNotFoundException ignored) {}
		return ret;
	}
	
	@Override
	public UUID getUUID() {
		return isPlayer() ? ((Player) sender).getUniqueId() : null;
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}
	
	@Override
	public boolean isPlayer() {
		return (sender instanceof Player);
	}
	
	@Override
	public String getName() {
		return sender.getName();
	}
	
	@Override
	public void sendMessage(String message, boolean colorTranslation) {
		if (isPlayer() && getPlugin().getJsonHandler().isJson(message))
			getPlugin().getJsonHandler().sendMessage(sender, message);
		else
			sender.sendMessage(colorTranslation ? ChatColor.translateAlternateColorCodes('&', message) : message);
	}
	
	@Override
	public void chat(String messageToSend) {
		if (isPlayer())
			((Player) sender).chat(messageToSend);
	}
}
