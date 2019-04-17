package com.alessiodp.core.bungeecord.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.messaging.MessageListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.NonNull;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public abstract class BungeeMessageListener extends MessageListener {
	private Listener listener;
	public BungeeMessageListener(@NonNull ADPPlugin plugin) {
		super(plugin);
		listener = new PacketListener();
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bungeePlugin = (Plugin) plugin.getBootstrap();
			bungeePlugin.getProxy().registerChannel(plugin.getMessenger().getChannel());
			bungeePlugin.getProxy().getPluginManager().registerListener(bungeePlugin, listener);
			registered = true;
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bungeePlugin = (Plugin) plugin.getBootstrap();
			bungeePlugin.getProxy().getPluginManager().unregisterListener(listener);
			registered = false;
		}
	}
	
	protected abstract void handlePacket(byte[] bytes);
	
	public class PacketListener implements Listener {
		@EventHandler
		public void onPluginMessageReceived(PluginMessageEvent event) {
			if (registered && event.getTag().equalsIgnoreCase(plugin.getMessenger().getChannel())) {
				ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
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
