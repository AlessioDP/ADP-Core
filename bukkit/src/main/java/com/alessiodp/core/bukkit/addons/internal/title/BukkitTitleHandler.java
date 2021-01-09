package com.alessiodp.core.bukkit.addons.internal.title;

import com.alessiodp.core.common.addons.internal.TitleHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class BukkitTitleHandler extends TitleHandler {
	private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	private final String nms = "net.minecraft.server." + serverVersion;
	
	@Override
	public boolean sendTitle(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime) {
		return sendTitleJson(user, "{\"text\": \"" + titleMessage + "\"}", fadeInTime, showTime, fadeOutTime);
	}
	
	@Override
	public boolean sendTitleJson(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime) {
		Player player = (Player) user;
		if (player != null) {
			try {
				Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, titleMessage);
				
				Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
				Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime / 50, showTime / 50, fadeOutTime / 50);
				
				sendPacket(player, packet);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		return false;
	}
	
	private void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Class<?> getNMSClass(String name) {
		try {
			return Class.forName(nms + "." + name);
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
