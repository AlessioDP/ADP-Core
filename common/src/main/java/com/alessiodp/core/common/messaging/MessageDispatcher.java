package com.alessiodp.core.common.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import com.alessiodp.core.common.user.User;
import lombok.Getter;
import lombok.NonNull;

public abstract class MessageDispatcher {
	protected final ADPPlugin plugin;
	protected boolean registered = false;
	@Getter protected String channel;
	
	public MessageDispatcher(@NonNull ADPPlugin plugin, boolean dispatchToBungeeCord) {
		this.plugin = plugin;
		channel = dispatchToBungeeCord ? "BungeeCord" : plugin.getPluginFallbackName() + ":main";
	}
	
	/**
	 * Register dispatcher
	 */
	public abstract void register();
	
	/**
	 * Unregister dispatcher
	 */
	public abstract void unregister();
	
	/**
	 * Send a packet
	 *
	 * @param packet the packet to send
	 * @return Returns true if sent successfully
	 */
	public boolean sendPacket(ADPPacket packet) {
		return sendPacket(packet, true);
	}
	
	/**
	 * Send a packet
	 *
	 * @param packet the packet to send
	 * @param log enable debug logs
	 * @return Returns true if sent successfully
	 */
	public abstract boolean sendPacket(ADPPacket packet, boolean log);
	
	/**
	 * Send a packet
	 *
	 * @param packet the packet to send
	 * @param user the user where to send the packet
	 * @return Returns true if sent successfully
	 */
	public boolean sendPacketToUser(ADPPacket packet, User user) {
		return sendPacketToUser(packet, user, true);
	}
	
	/**
	 * Send a packet
	 *
	 * @param packet the packet to send
	 * @param user the user where to send the packet
	 * @param log enable debug logs
	 * @return Returns true if sent successfully
	 */
	public abstract boolean sendPacketToUser(ADPPacket packet, User user, boolean log);
	
	
	/**
	 * Send a forward packet that will be dispatched to every server.
	 * This is supported by BungeeCord channel only.
	 *
	 * @param packet the packet to send
	 * @return Returns true if sent successfully
	 */
	public boolean sendForwardPacket(ADPPacket packet) {
		return sendForwardPacket(packet, true);
	}
	
	/**
	 * Send a forward packet that will be dispatched to every server.
	 * This is supported by BungeeCord channel only.
	 *
	 * @param packet the packet to send
	 * @param log enable debug logs
	 * @return Returns true if sent successfully
	 */
	public abstract boolean sendForwardPacket(ADPPacket packet, boolean log);
	
	/**
	 * Send an error about the exception
	 *
	 * @param exception The raised exception
	 */
	protected void sendError(Exception exception) {
		plugin.getLoggerManager().printError(String.format(Constants.DEBUG_LOG_MESSAGING_FAILED_SEND,
				exception.getMessage() != null ? exception.getMessage() : exception.getStackTrace()[0].toString()));
	}
}
