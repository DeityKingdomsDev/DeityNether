package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.nether.helpers.WorldHelper;

public class LeaveSubCommand extends DeityCommandReceiver {
	Player player;
	
	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player arg0, String[] arg1) {
		if(!player.getWorld().getName().equalsIgnoreCase("world")) {
			WorldHelper.removePlayer(player);
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityNether", "Welcome back to the main world! To see how ");
			return true;
		} else {
			return false;
		}
	}

}
