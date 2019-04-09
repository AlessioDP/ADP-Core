package com.alessiodp.core.common.addons.internal;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import com.alessiodp.core.common.configuration.Constants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ADPUpdater {
	@NonNull private final ADPPlugin plugin;
	
	private String pluginName;
	private String pluginResourceId;
	private boolean checkForUpdates;
	private boolean warnUpdates;
	private String warnPermission;
	private String warnMessage;
	
	@Getter private String foundVersion = "";
	
	/**
	 * Reload updater
	 *
	 * @param pluginName name of the plugin
	 * @param pluginResourceId id of the spigot plugin
	 * @param checkForUpdates toggle check for updates
	 * @param warnUpdates toggle warnings on new updates
	 * @param warnPermission permission necessary to receive the warning
	 * @param warnMessage the warning message
	 */
	public void reload(String pluginName, String pluginResourceId, boolean checkForUpdates, boolean warnUpdates, String warnPermission, String warnMessage) {
		this.pluginName = pluginName;
		this.pluginResourceId = pluginResourceId;
		this.checkForUpdates = checkForUpdates;
		this.warnUpdates = warnUpdates;
		this.warnPermission = warnPermission;
		this.warnMessage = warnMessage;
	}
	
	/**
	 * Alert all players
	 */
	private void alertPlayers() {
		if (warnUpdates && !foundVersion.isEmpty()) {
			for (User player : plugin.getOnlinePlayers()) {
				if (player.hasPermission(warnPermission)) {
					player.sendMessage(warnMessage
							.replace("%version%", foundVersion)
							.replace("%thisversion%", plugin.getVersion()), true);
				}
			}
		}
	}
	
	/**
	 * Make asynchronous task to check for updates every day
	 */
	public void asyncTaskCheckUpdates() {
		if (checkForUpdates) {
			plugin.getScheduler().scheduleAsyncRepeating(this::checkUpdates,0, 1, TimeUnit.DAYS);
		}
	}
	
	/**
	 * Asynchronous check for updates
	 */
	public void asyncCheckUpdates() {
		if (checkForUpdates) {
			plugin.getScheduler().runAsync(this::checkUpdates);
		}
	}
	
	/**
	 * Check for updates method
	 */
	private void checkUpdates() {
		foundVersion = "";
		String remoteVersion = getVersionInfo();
		
		if (remoteVersion == null) {
			plugin.getLoggerManager().log(Constants.UPDATER_FALLBACK_WARN
					.replace("{plugin}", plugin.getPluginName()), true);
			remoteVersion = getVersionFallback();
		}
		
		if (remoteVersion != null
				&& checkVersion(remoteVersion, plugin.getVersion())) {
			// New version found
			foundVersion = remoteVersion;
			
			plugin.getLoggerManager().log(Constants.UPDATER_FOUND
					.replace("{plugin}", plugin.getPluginName())
					.replace("{currentVersion}", plugin.getVersion())
					.replace("{newVersion}", foundVersion), true);
			alertPlayers();
		}
	}
	
	/**
	 * Get latest version from alessiodp.com
	 *
	 * @return the latest version string
	 */
	private String getVersionInfo() {
		String ret = null;
		try {
			URLConnection conn = new URL(Constants.UPDATER_URL
					.replace("{plugin}", plugin.getPluginFallbackName())
					.replace("{version}", plugin.getVersion())).openConnection();
			conn.setConnectTimeout(10000);
			conn.addRequestProperty("User-Agent", "ADP Updater");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JsonObject response = new JsonParser().parse(br.readLine()).getAsJsonObject();
			// Get the version string
			if (response != null)
				ret = response.get(Constants.UPDATER_FIELD_VERSION
						.replace("{plugin}", plugin.getPluginName())).getAsString();
			
			br.close();
		} catch (IOException ex) {
			plugin.getLoggerManager().printError(Constants.UPDATER_FAILED_IO
					.replace("{plugin}", plugin.getPluginName()));
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.UPDATER_FAILED_GENERAL
					.replace("{plugin}", plugin.getPluginName()));
		}
		return ret;
	}
	
	/**
	 * Get latest version from spigot
	 *
	 * @return the latest version string
	 */
	private String getVersionFallback() {
		String ret = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(Constants.UPDATER_FALLBACK_URL).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.addRequestProperty("User-Agent", "ADP Updater");
			
			String postContent = "key=" +
					Constants.UPDATER_FALLBACK_KEY +
					"&resource=" +
					pluginResourceId;
			conn.getOutputStream().write(postContent.getBytes(StandardCharsets.UTF_8));
			
			String response = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
			// Check if is a correct version and not a message
			if (response.length() < 10) {
				ret = response;
			}
		} catch (IOException ex) {
			plugin.getLoggerManager().printError(Constants.UPDATER_FAILED_IO
					.replace("{plugin}", plugin.getPluginName()));
		} catch (Exception ex) {
			plugin.getLoggerManager().printError(Constants.UPDATER_FAILED_GENERAL
					.replace("{plugin}", plugin.getPluginName()));
		}
		return ret;
	}
	
	/**
	 * Parse latest version
	 *
	 * @param remoteVersion current version
	 * @param currentVersion string version to compare with the current one
	 * @return true if the version is newer than the given one
	 */
	private boolean checkVersion(String remoteVersion, String currentVersion) {
		boolean ret = false;
		String[] splitVer = splitVersion(remoteVersion);
		String[] splitCompareWith = splitVersion(currentVersion);
		
		try {
			for (int c=0; c < splitVer.length && !ret; c++) {
				int a = Integer.parseInt(splitVer[c]);
				int b = c < splitCompareWith.length ? Integer.parseInt(splitCompareWith[c]) : 0;
				if (a > b)
					ret = true;
				else if (a < b)
					break;
			}
		} catch (Exception ex) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * Split version into string array
	 *
	 * @param version version string
	 * @return the version split as array of strings
	 */
	private String[] splitVersion(String version) {
		String ret = version.split(Constants.UPDATER_DELIMITER_TYPE)[0];
		return ret.split(Constants.UPDATER_DELIMITER_VERSION);
	}
}