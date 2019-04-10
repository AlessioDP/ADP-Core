package com.alessiodp.core.bukkit.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.messaging.ADPPacket;
import com.alessiodp.core.common.messaging.MessageDispatcher;
import com.google.common.collect.Iterables;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public abstract class BukkitMessageDispatcher extends MessageDispatcher {
	
	public BukkitMessageDispatcher(@NonNull ADPPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().registerOutgoingPluginChannel(bukkitPlugin, Constants.MESSAGING_CHANNEL);
			registered = true;
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().unregisterOutgoingPluginChannel(bukkitPlugin);
			registered = false;
		}
	}
	
	public void sendPacketToAllServers(ADPPacket packet) {
		Player dummyPlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (dummyPlayer != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			try {
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				out.writeUTF(plugin.getPluginName());
				
				byte[] bytes = packet.build();
				out.writeShort(bytes.length);
				out.write(bytes);
				
				dummyPlayer.sendPluginMessage((Plugin) plugin.getBootstrap(), Constants.MESSAGING_CHANNEL, baos.toByteArray());
			} catch (Exception ex) {
				plugin.getLoggerManager().printError(Constants.DEBUG_LOG_MESSAGING_FAILED
						.replace("{message}", ex.getMessage()));
			}
		}
	}
}
