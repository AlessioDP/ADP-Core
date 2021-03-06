package com.alessiodp.core.common.messaging;

import com.alessiodp.core.common.ADPPlugin;
import lombok.Getter;
import lombok.NonNull;

public abstract class ADPMessenger {
	protected final ADPPlugin plugin;
	@Getter protected MessageDispatcher messageDispatcher;
	@Getter protected MessageListener messageListener;
	
	public ADPMessenger(@NonNull ADPPlugin plugin) {
		this.plugin = plugin;
	}
	
	public abstract void reload();
	
	/**
	 * Unregister dispatcher and listener
	 */
	public final void disable() {
		if (messageDispatcher != null)
			messageDispatcher.unregister();
		
		if (messageListener != null)
			messageListener.unregister();
	}
}
