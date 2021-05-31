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
	@Getter protected String mainChannel;
	@Getter protected String subChannel;
	@Getter protected String bungeeCordChannel;
	
	public MessageListener(@NonNull ADPPlugin plugin, boolean listenToMain, boolean listenToSub, boolean listenToBungeeCord) {
		this.plugin = plugin;
		if (listenToMain)
			mainChannel = plugin.getPluginFallbackName() + ":main";
		if (listenToSub)
			subChannel = plugin.getPluginFallbackName() + ":sub";
		if (listenToBungeeCord)
			bungeeCordChannel = "BungeeCord";
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
