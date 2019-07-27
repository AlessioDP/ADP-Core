package com.alessiodp.core.common.messaging;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MessageDispatcher {
	@NonNull protected final ADPPlugin plugin;
	protected boolean registered = false;
	
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
	public abstract boolean sendPacket(ADPPacket packet);
	
	/**
	 * Send a forward packet that will be dispatched to every server.
	 * This is supported by BungeeCord channel only.
	 *
	 * @param packet the packet to send
	 * @return Returns true if sent successfully
	 */
	public abstract boolean sendForwardPacket(ADPPacket packet);
}
