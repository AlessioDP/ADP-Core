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
	private final PluginMessageListener listener;
	
	public BukkitMessageListener(@NonNull ADPPlugin plugin, boolean listenToBungeeCord) {
		super(plugin, listenToBungeeCord);
		listener = new PacketListener();
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, getChannel(), listener);
			registered = true;
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().unregisterIncomingPluginChannel(bukkitPlugin);
			registered = false;
		}
	}
	
	protected abstract void handlePacket(byte[] bytes);
	
	public class PacketListener implements PluginMessageListener {
		@EventHandler
		public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
			boolean isBungeeCord = channel.equalsIgnoreCase("BungeeCord");
			if (isBungeeCord || channel.equals(getChannel()))  {
				ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
				if (isBungeeCord) {
					String subchannel = input.readUTF();
					if (!subchannel.equals(plugin.getPluginFallbackName()))
						return;
				}
				
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
