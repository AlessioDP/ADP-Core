package com.alessiodp.core.bungeecord.messaging;

import com.alessiodp.core.bungeecord.user.BungeeUser;
import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.messaging.ADPPacket;
import com.alessiodp.core.common.messaging.MessageDispatcher;
import com.alessiodp.core.common.user.User;
import com.google.common.collect.Iterables;
import lombok.NonNull;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public abstract class BungeeMessageDispatcher extends MessageDispatcher {
	
	public BungeeMessageDispatcher(@NonNull ADPPlugin plugin, boolean dispatchToBungeeCord) {
		super(plugin, dispatchToBungeeCord);
	}
	
	@Override
	public void register() {
		if (!registered) {
			Plugin bungeePlugin = (Plugin) plugin.getBootstrap();
			bungeePlugin.getProxy().registerChannel(getChannel());
			registered = true;
		}
	}
	
	@Override
	public void unregister() {
		if (registered) {
			Plugin bungeePlugin = (Plugin) plugin.getBootstrap();
			bungeePlugin.getProxy().unregisterChannel(getChannel());
			registered = false;
		}
	}
	
	@Override
	public boolean sendPacket(ADPPacket packet, boolean log) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			byte[] bytes = packet.build();
			out.writeShort(bytes.length);
			out.write(bytes);
			
			for (ServerInfo si : ((Plugin) plugin.getBootstrap()).getProxy().getServers().values()) {
				si.sendData(getChannel(), baos.toByteArray());
			}
			
			if (log)
				plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT, packet.getName(), "*", getChannel()), true);
			return true;
		} catch (Exception ex) {
			sendError(ex);
			ex.printStackTrace();
		}
		
		if (log)
			plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT_FAILED, packet.getName()), true);
		return false;
	}
	
	@Override
	public boolean sendPacketToUser(ADPPacket packet, User user, boolean log) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			byte[] bytes = packet.build();
			out.writeShort(bytes.length);
			out.write(bytes);
			
			((BungeeUser) user).getServer().sendData(getChannel(), baos.toByteArray());
			
			if (log)
				plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT, packet.getName(), user.getUUID(), getChannel()), true);
			return true;
		} catch (Exception ex) {
			sendError(ex);
			ex.printStackTrace();
		}
		
		if (log)
			plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT_FAILED, packet.getName()), true);
		return false;
	}
	
	@Override
	public boolean sendForwardPacket(ADPPacket packet, boolean log) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			// Required fields for forward
			out.writeUTF("Forward");
			out.writeUTF("ALL");
			out.writeUTF(plugin.getPluginFallbackName());
			
			byte[] bytes = packet.build();
			out.writeShort(bytes.length);
			out.write(bytes);
			
			ProxiedPlayer dummyPlayer = Iterables.getFirst(((Plugin) plugin.getBootstrap()).getProxy().getPlayers(), null);
			
			if (dummyPlayer != null) {
				dummyPlayer.sendData(getChannel(), baos.toByteArray());
				
				if (log)
					plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT, packet.getName(), "*", getChannel()), true);
				return true;
			}
		} catch (Exception ex) {
			sendError(ex);
			ex.printStackTrace();
		}
		
		if (log)
			plugin.getLoggerManager().logDebug(String.format(Constants.DEBUG_LOG_MESSAGING_SENT_FAILED, packet.getName()), true);
		return false;
	}
}
