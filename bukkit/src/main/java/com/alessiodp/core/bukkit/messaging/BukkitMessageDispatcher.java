package com.alessiodp.core.bukkit.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.messaging.ADPPacket;
import com.alessiodp.core.common.messaging.MessageDispatcher;
import com.alessiodp.core.common.user.User;
import com.google.common.collect.Iterables;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BukkitMessageDispatcher extends MessageDispatcher {
	
	public BukkitMessageDispatcher(@NonNull ADPPlugin plugin, boolean dispatchToBungeeCord) {
		super(plugin, dispatchToBungeeCord);
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bukkitPlugin = (Plugin) plugin.getBootstrap();
			bukkitPlugin.getServer().getMessenger().registerOutgoingPluginChannel(bukkitPlugin, getChannel());
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
	
	@Override
	public boolean sendPacket(ADPPacket packet, boolean log) {
		Player dummyPlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (dummyPlayer != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			try {
				byte[] bytes = packet.build();
				out.writeShort(bytes.length);
				out.write(bytes);
				
				dummyPlayer.sendPluginMessage((Plugin) plugin.getBootstrap(), getChannel(), baos.toByteArray());
				
				if (log)
					plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT, packet.getName(), dummyPlayer.getUniqueId().toString()), true);
				return true;
			} catch (Exception ex) {
				sendError(ex);
			}
		}
		
		if (log)
			plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT_FAILED, packet.getName()), true);
		return false;
	}
	
	@Override
	public boolean sendPacketToUser(ADPPacket packet, User user, boolean log) {
		// Not supported
		return sendPacket(packet, log);
	}
	
	@Override
	public boolean sendForwardPacket(ADPPacket packet, boolean log) {
		Player dummyPlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (dummyPlayer != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			try {
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				// Set sub channel as plugin name
				out.writeUTF(plugin.getPluginFallbackName());
				
				byte[] bytes = packet.build();
				out.writeShort(bytes.length);
				out.write(bytes);
				
				dummyPlayer.sendPluginMessage((Plugin) plugin.getBootstrap(), getChannel(), baos.toByteArray());
				
				if (log)
					plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT, packet.getName(), dummyPlayer.getUniqueId().toString()), true);
				return true;
			} catch (Exception ex) {
				sendError(ex);
			}
		}
		
		if (log)
			plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT_FAILED, packet.getName()), true);
		return false;
	}
}
