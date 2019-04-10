package com.alessiodp.core.bukkit.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.messaging.MessageListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import static com.alessiodp.core.common.configuration.Constants.MESSAGING_CHANNEL;

public abstract class BukkitMessageListener extends MessageListener implements PluginMessageListener {
	
	public BukkitMessageListener(@NonNull ADPPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, MESSAGING_CHANNEL, this);
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().unregisterIncomingPluginChannel(bukkitPlugin);
		}
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		if (channel.equals(MESSAGING_CHANNEL)) {
			ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
			String subchannel = input.readUTF();
			// Check subchannel
			if (subchannel.equals(plugin.getPluginName())) {
				plugin.getScheduler().runAsync(() -> {
					short packetLength = input.readShort();
					byte[] packetBytes = new byte[packetLength];
					input.readFully(packetBytes);
					handlePacket(packetBytes);
				});
			}
		}
	}
	
	public abstract void handlePacket(byte[] bytes);
}
