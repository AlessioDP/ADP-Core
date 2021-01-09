package com.alessiodp.core.common.messaging;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MessageListener {
	@NonNull protected final ADPPlugin plugin;
	protected boolean registered = false;
	@Getter protected String channel;
	
	public MessageListener(@NonNull ADPPlugin plugin, boolean listenToBungeeCord) {
		this.plugin = plugin;
		channel = listenToBungeeCord ? "BungeeCord" : plugin.getPluginFallbackName() + ":main";
	}
	
	/**
	 * Register listener
	 */
	public abstract void register();
	
	/**
	 * Unregister listener
	 */
	public abstract void unregister();
	
	/**
	 * Send an error about the exception
	 *
	 * @param exception The raised exception
	 */
	protected void sendError(Exception exception) {
		plugin.getLoggerManager().printError(String.format(Constants.DEBUG_LOG_MESSAGING_FAILED_READ,
				exception.getMessage() != null ? exception.getMessage() : exception.getStackTrace()[0].toString()));
	}
}
