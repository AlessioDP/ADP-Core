package com.alessiodp.core.common.addons;

import com.alessiodp.core.common.ADPPlugin;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class AddonManager {
	@NonNull protected final ADPPlugin plugin;
	
	/**
	 * Load all addons
	 */
	public abstract void loadAddons();
}