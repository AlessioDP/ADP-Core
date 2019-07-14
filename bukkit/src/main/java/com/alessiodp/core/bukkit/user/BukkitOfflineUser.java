package com.alessiodp.core.bukkit.user;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.OfflineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@AllArgsConstructor
public class BukkitOfflineUser implements OfflineUser {
	@Getter private final ADPPlugin plugin;
	private final OfflinePlayer player;
	
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
