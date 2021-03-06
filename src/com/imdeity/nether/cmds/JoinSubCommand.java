package com.imdeity.nether.cmds;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.nether.DeityNether;
import com.imdeity.nether.helpers.PlayerInventoryChecker;
import com.imdeity.nether.helpers.WorldHelper;
import com.imdeity.nether.sql.NetherSQL;

public class JoinSubCommand extends DeityCommandReceiver {
	int netherTimeLimit = DeityNether.plugin.getNetherTimeLimit();	
	String mainWorld = DeityNether.plugin.getWorldName();
	
	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		int timeSpent = NetherSQL.getTimeSpent(player);
		if(player.getWorld().getName().equalsIgnoreCase(mainWorld)) {//Making sure that the player isn't already in the nether
			if(timeSpent<netherTimeLimit) {//Time check
				if(PlayerInventoryChecker.checkInventory(player)) {//Inventory check
					WorldHelper.addPlayer(player);
					DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityNether", "Welcome to the nether!");
					return true;
				} else {//If they have illegal items
					DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityNether", "You may only bring tools, swords, armor, and food into the nether. Two gold blocks will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
					return true;
				}
			} else {//If they don't have time
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityNether", "You don't have any time left in the nether! You must wait 24 hours to reenter the nether.");
				return true;
			}
		} else {//If they are already in the nether
			DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityNether", "You are already in the nether!");
		}
		return false;
	}
}
