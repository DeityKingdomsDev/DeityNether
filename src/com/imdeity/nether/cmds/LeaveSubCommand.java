package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.nether.DeityNether;
import com.imdeity.nether.helpers.WorldHelper;

public class LeaveSubCommand extends DeityCommandReceiver {
	String netherWorld = DeityNether.plugin.getNetherName();
	
	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] arg1) {
		if(player.getWorld().getName().equalsIgnoreCase(netherWorld)) {//if the player is in the nether
			WorldHelper.removePlayer(player);
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityNether", "Welcome back to the main world! To see how ");
		} else {
			DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityNether", "You are already in the main world!");
		}
		return false;
	}

}
