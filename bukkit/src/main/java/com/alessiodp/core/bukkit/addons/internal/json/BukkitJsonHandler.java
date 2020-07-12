package com.alessiodp.core.bukkit.addons.internal.json;

import com.alessiodp.core.common.addons.internal.JsonHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Bukkit handler for JSON handling
 */
public class BukkitJsonHandler extends JsonHandler {
	private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
	@Override
	public boolean sendMessage(Object user, String jsonMessage) {
		boolean ret = false;
		Player player = (Player) user;
		if (player != null) {
			try {
				String nms = "net.minecraft.server." + serverVersion;
				String obc = "org.bukkit.craftbukkit." + serverVersion;
				
				Object parsedMessage = Class.forName(nms + ".IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, jsonMessage);
				Object packetPlayOutChat;
				try {
					packetPlayOutChat = Class.forName(nms + ".PacketPlayOutChat")
							.getConstructor(Class.forName(nms + ".IChatBaseComponent")).newInstance(parsedMessage);
				} catch (NoSuchMethodException ignored) {
					// Minecraft 1.16
					packetPlayOutChat = Class.forName(nms + ".PacketPlayOutChat")
							.getConstructor(
									Class.forName(nms + ".IChatBaseComponent"),
									Class.forName(nms + ".ChatMessageType"),
									UUID.class
							)
							.newInstance(
									parsedMessage,
									Enum.valueOf((Class<Enum>) Class.forName(nms + ".ChatMessageType"), "SYSTEM"),
									player.getUniqueId()
							);
				}
				
				Object craftPlayer = Class.forName(obc + ".entity.CraftPlayer").cast(player);
				Object craftHandle = Class.forName(obc + ".entity.CraftPlayer").getMethod("getHandle").invoke(craftPlayer);
				Object playerConnection = Class.forName(nms + ".EntityPlayer").getField("playerConnection").get(craftHandle);
				
				Class.forName(nms + ".PlayerConnection").getMethod("sendPacket",
						Class.forName(nms + ".Packet")).invoke(playerConnection, packetPlayOutChat);
				ret = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}
}