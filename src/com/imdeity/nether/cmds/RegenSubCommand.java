package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;


public class RegenSubCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		DeityAPI.getAPI().getChatAPI().sendGlobalMessage("DeityNether", "The nether is now regenerating...");
		//Set regen config stuff
		//Shut down
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player arg0, String[] arg1) {
		// TODO Copy from above when complete
		return false;
	}

}
