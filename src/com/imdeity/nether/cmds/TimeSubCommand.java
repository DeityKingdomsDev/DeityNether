package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.nether.DeityNether;
import com.imdeity.nether.sql.NetherSQL;

public class TimeSubCommand extends DeityCommandReceiver {
	Player player;
	int timeSpent = NetherSQL.getTimeSpent(player);
	int netherTimeLimit = DeityNether.plugin.getNetherTimeLimit();
	
	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player arg0, String[] arg1) {
		int timeLeft = netherTimeLimit-timeSpent;
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityNether", "You have " + timeLeft + "minutes left in the nether!");
		return true;
	}

}
