package com.imdeity.nether.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.imdeity.nether.DeityNether;
import com.imdeity.nether.sql.NetherSQL;

public class PlayerChecker implements Runnable {
	
	public static HashMap<Player, Long> playerMap = new HashMap<Player, Long>(); //Stores a player object and the system time
	public static ArrayList<Player> playersInNether = new ArrayList<Player>(); //A list of players in the nether

	public void run() {
		
		for(int i = 0; i < playersInNether.size(); i++) { //Go through all players in the nether without having to go through all entities in the nether
			if(System.currentTimeMillis() - playerMap.get(playersInNether.get(i)) > DeityNether.plugin.getNetherTimeLimit()) {
				//if the player has spent over an hour in the nether:
				WorldHelper.removePlayer(playersInNether.get(i)); //move them back to the main world
				NetherSQL.removePlayer(playersInNether.get(i)); //and if they need to, log and update the database (perms tested in the sql class
			}
		}
		
	}
	
	public static void addPlayer(Player p) {
		//add a player into the hashmap and arraylist
		playerMap.put(p, System.currentTimeMillis());
		playersInNether.add(p);
	}
	
	public static void removePlayer(Player p) {
		//remove a player from the hashmap and arraylist
		playerMap.remove(p);
		playersInNether.remove(p);
	}

}
