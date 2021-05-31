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
	
	public BukkitMessageListener(@NonNull ADPPlugin plugin, boolean listenToMain, boolean listenToSub, boolean listenToBungeeCord) {
		super(plugin, listenToMain, listenToSub, listenToBungeeCord);
		listener = new PacketListener();
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			if (getMainChannel() != null)
				bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, getMainChannel(), listener);
			if (getSubChannel() != null)
				bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, getSubChannel(), listener);
			if (getBungeeCordChannel() != null)
				bukkitPlugin.getServer().getMessenger().registerIncomingPluginChannel(bukkitPlugin, getBungeeCordChannel(), listener);
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
	
	protected abstract void handlePacket(byte[] bytes, String channel);
	
	protected void handleBungeeCordPacket(byte[] bytes) {
		// Nothing to do by default
	}
	
	public class PacketListener implements PluginMessageListener {
		@EventHandler
		public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
			boolean isBungeeCord = channel.equalsIgnoreCase(getBungeeCordChannel());
			if (isBungeeCord || channel.equals(getMainChannel()) || channel.equals(getSubChannel()))  {
				ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
				if (isBungeeCord) {
					String subchannel = input.readUTF();
					if (subchannel.equals(plugin.getPluginFallbackName())) {
						plugin.getScheduler().runAsync(() -> {
							short packetLength = input.readShort();
							byte[] packetBytes = new byte[packetLength];
							input.readFully(packetBytes);
							handleBungeeCordPacket(packetBytes);
						});
					}
				} else {
					plugin.getScheduler().runAsync(() -> {
						short packetLength = input.readShort();
						byte[] packetBytes = new byte[packetLength];
						input.readFully(packetBytes);
						handlePacket(packetBytes, channel);
					});
				}
			}
		}
	}
}
