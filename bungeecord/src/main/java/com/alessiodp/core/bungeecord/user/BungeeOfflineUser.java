package com.alessiodp.core.bungeecord.user;

import com.alessiodp.core.common.user.OfflineUser;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeeOfflineUser implements OfflineUser {
	private final ProxiedPlayer player;
	private final UUID uuid;
	
	public BungeeOfflineUser(@NonNull ProxiedPlayer player, UUID uuid) {
		this.player = player;
		this.uuid = uuid;
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	@Override
	public boolean isOnline() {
		return player != null;
	}
	
	@Override
	public String getName() {
		return player != null ? player.getName() : "";
	}
	
}