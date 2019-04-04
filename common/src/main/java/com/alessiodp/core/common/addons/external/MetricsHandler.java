package com.alessiodp.core.common.addons.external;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.configuration.Constants;
import lombok.AllArgsConstructor;
import lombok.NonNull;

public abstract class MetricsHandler {
	protected final ADPPlugin plugin;
	
	public MetricsHandler(@NonNull ADPPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getLoggerManager().logDebug(Constants.DEBUG_ADDON_METRICS_INIT, true);
		registerMetrics();
	}
	
	/**
	 * Register metrics
	 */
	protected abstract void registerMetrics();
}
