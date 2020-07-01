package com.alessiodp.core.common.commands.utils;

import com.alessiodp.core.common.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public abstract class CommandData {
	@Getter @Setter private User sender;
	@Getter @Setter private String commandLabel;
	@Getter @Setter private String[] args;
	private final HashMap<ADPPermission, Boolean> permissionsPayload = new HashMap<>();
	
	/**
	 * Add permission to the payload
	 *
	 * @param permission the permission to add
	 */
	public void addPermission(ADPPermission permission) {
		permissionsPayload.put(permission, sender.hasPermission(permission));
	}
	
	/**
	 * Does already exist the permission?
	 *
	 * @param permission the permission to check
	 * @return true if the permission exists
	 */
	public boolean havePermission(ADPPermission permission) {
		if (permissionsPayload.containsKey(permission))
			return permissionsPayload.get(permission);
		return false;
	}
}
