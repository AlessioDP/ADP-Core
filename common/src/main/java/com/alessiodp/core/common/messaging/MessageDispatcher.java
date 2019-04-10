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
}
