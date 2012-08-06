package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.nether.DeityNether;


public class RegenSubCommand extends DeityCommandReceiver {
	Player player;
	
	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		DeityAPI.getAPI().getChatAPI().sendGlobalMessage("DeityNether", "The nether is now regenerating...");
		DeityNether.plugin.config.set("last-reset", -1);
		DeityNether.plugin.getServer().shutdown();
		return true;
	}

	@Override
	public boolean onPlayerRunCommand(Player arg0, String[] arg1) {
			DeityAPI.getAPI().getChatAPI().sendGlobalMessage("DeityNether", "The nether is now regenerating...");
			DeityNether.plugin.config.set("last-reset", -1);
			DeityNether.plugin.getServer().shutdown();
			return true;
	}

}
