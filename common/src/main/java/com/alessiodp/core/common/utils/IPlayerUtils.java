package com.alessiodp.core.common.utils;

import com.alessiodp.core.common.commands.list.ADPCommand;
import com.alessiodp.core.common.user.User;
import lombok.NonNull;

import java.util.List;

public interface IPlayerUtils {
	/**
	 * Get a list of player allowed commands
	 *
	 * @param user the player to handle
	 * @return a list of commands allowed by the player
	 */
	List<ADPCommand> getAllowedCommands(@NonNull User user);
}
