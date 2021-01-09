package com.alessiodp.core.common.utils;

import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.user.User;
import lombok.NonNull;

import java.util.Set;

public interface IPlayerUtils {
	/**
	 * Get a set of player allowed commands
	 *
	 * @param user the player to handle
	 * @return a set of commands allowed by the player
	 */
	Set<ADPCommand> getAllowedCommands(@NonNull User user);
}
