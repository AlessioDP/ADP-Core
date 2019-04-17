package com.alessiodp.core.bukkit.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.messaging.MessageListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public abstract class BukkitMessageListener extends MessageListener {
	private PluginMessageListener listener;
	
	public BukkitMessageListener(@NonNull ADPPlugin plugin) {
		super(plugin);
		listener = new PacketListener();
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, plugin.getMessenger().getChannel(), listener);
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().unregisterIncomingPluginChannel(bukkitPlugin);
		}
	}
	
	protected abstract void handlePacket(byte[] bytes);
	
	public class PacketListener implements PluginMessageListener {
		@EventHandler
		public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
			if (channel.equals(plugin.getMessenger().getChannel()))  {
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
	}
}
