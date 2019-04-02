package com.alessiodp.core.common.addons.external;

import com.alessiodp.core.common.ADPPlugin;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class MetricsHandler {
	@NonNull protected final ADPPlugin plugin;
	
	/**
	 * Register metrics
	 */
	public abstract void registerMetrics();
}
