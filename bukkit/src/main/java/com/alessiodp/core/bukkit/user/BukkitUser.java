package com.alessiodp.core.bukkit.user;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.utils.Color;
import io.papermc.lib.PaperLib;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class BukkitUser implements User {
	private final ADPPlugin plugin;
	private final CommandSender sender;
	
	@Override
	public UUID getUUID() {
		return isPlayer() ? ((Player) sender).getUniqueId() : null;
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}
	
	@Override
	public String getDynamicPermission(String startsWith) {
		for (PermissionAttachmentInfo pa : sender.getEffectivePermissions()) {
			String perm = pa.getPermission();
			if (perm.startsWith(startsWith)) {
				return perm.substring(startsWith.length());
			}
		}
		return null;
	}
	
	@Override
	public boolean isOperator() {
		return sender.isOp();
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
			getPlugin().getJsonHandler().sendMessage(sender, Color.translateAlternateColorCodes(message));
		else
			sender.sendMessage(colorTranslation ? Color.translateAlternateColorCodes(message) : message);
	}
	
	@Override
	public void chat(String messageToSend) {
		if (isPlayer())
			((Player) sender).chat(messageToSend);
	}
	
	@Override
	public void playSound(String sound, double volume, double pitch) {
		try {
			Sound s = Sound.valueOf(sound);
			((Player) sender).playSound(((Player) sender).getLocation(), s, (float) volume, (float) pitch);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public boolean isVanished() {
		for (MetadataValue meta : ((Player) sender).getMetadata("vanished")) {
			if (meta.asBoolean()) return true;
		}
		return false;
	}
	
	@Override
	public ADPPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Get original sender
	 *
	 * @return the sender
	 */
	public CommandSender getSender() {
		return sender;
	}
	
	/**
	 * Teleport the player with an async teleport
	 *
	 * @param location the destination of the teleport
	 * @return the future event
	 */
	public CompletableFuture<Boolean> teleportAsync(Location location) {
		return PaperLib.teleportAsync((Player) sender, location);
	}
}
