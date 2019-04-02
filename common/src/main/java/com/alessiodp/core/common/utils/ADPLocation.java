package com.alessiodp.core.common.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Class used as container for locations
 */
public class ADPLocation {
	@Getter @Setter private String world;
	@Getter @Setter private double x;
	@Getter @Setter private double y;
	@Getter @Setter private double z;
	@Getter @Setter private float yaw;
	@Getter @Setter private float pitch;
	
	public ADPLocation(@NonNull String serialized) throws IllegalArgumentException {
		String[] split = serialized.split(",");
		if (split.length != 6) throw new IllegalArgumentException("failed to deserialize location '" + serialized + "'");
		
		world = split[0];
		x = Double.parseDouble(split[1]);
		y = Double.parseDouble(split[2]);
		z = Double.parseDouble(split[3]);
		yaw = Float.parseFloat(split[4]);
		pitch = Float.parseFloat(split[5]);
	}
	
	public ADPLocation(String world, double x, double y, double z, float yaw, float pitch) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	/**
	 * Return a new location incremented axes
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @return a new location
	 */
	public ADPLocation add(double x, double y, double z) {
		return new ADPLocation(
				world,
				this.x + x,
				this.y + y,
				this.z + z,
				yaw,
				pitch
		);
	}
	
	/**
	 * Deserialize a string location
	 *
	 * @param serialized the location serialized
	 * @return a new parsed location or null
	 */
	public static ADPLocation deserialize(String serialized) {
		ADPLocation ret = null;
		if (!serialized.isEmpty())
			try {
				ret = new ADPLocation(serialized);
			} catch (Exception ignored) {}
		return ret;
	}
	
	@Override
	public String toString() {
		return this.getWorld() + "," +
				this.getX() + "," +
				this.getY() + "," +
				this.getZ() + "," +
				this.getYaw() + "," +
				this.getPitch();
	}
}
