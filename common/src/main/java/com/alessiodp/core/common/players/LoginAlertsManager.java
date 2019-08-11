package com.alessiodp.core.common.players;

import com.alessiodp.core.common.commands.utils.ADPPermission;
import com.alessiodp.core.common.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class LoginAlertsManager {
	@Getter private ArrayList<String> loginAlerts;
	@Setter private ADPPermission permission = null;
	
	public LoginAlertsManager() {
		loginAlerts = new ArrayList<>();
	}
	
	public void reload() {
		loginAlerts.clear();
	}
	
	public void sendAlerts(User user) {
		if (user != null
				&& permission != null
				&& user.hasPermission(permission.toString())) {
			for (String alert : loginAlerts) {
				user.sendMessage(alert, true);
			}
		}
	}
}
