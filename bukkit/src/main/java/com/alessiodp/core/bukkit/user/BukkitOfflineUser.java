package com.alessiodp.core.bukkit.user;

import com.alessiodp.core.common.user.OfflineUser;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class BukkitOfflineUser implements OfflineUser {
	private final OfflinePlayer player;
	
	public BukkitOfflineUser(@NonNull OfflinePlayer offlinePlayer) {
		player = offlinePlayer;
	}
	
	@Override
	public UUID getUUID() {
		return player.getUniqueId();
	}
	
	@Override
	public boolean isOnline() {
		return player.isOnline();
	}
	
	@Override
	public String getName() {
		return player.getName();
	}
	
}
