package com.alessiodp.core.common.messaging;

import com.alessiodp.core.common.ADPPlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MessageListener {
	@NonNull protected final ADPPlugin plugin;
	protected boolean registered = false;
	
	/**
	 * Register listener
	 */
	public abstract void register();
	
	/**
	 * Unregister listener
	 */
	public abstract void unregister();
}
