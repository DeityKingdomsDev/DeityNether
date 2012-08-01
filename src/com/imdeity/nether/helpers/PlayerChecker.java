package com.imdeity.nether.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.imdeity.nether.DeityNether;
import com.imdeity.nether.sql.NetherSQL;

public class PlayerChecker implements Runnable {
	
	public static HashMap<Player, Long> playerMap = new HashMap<Player, Long>();
	public static ArrayList<Player> playersInNether = new ArrayList<Player>();

	public void run() {
		
		for(int i = 0; i < playersInNether.size(); i++) {
			if(System.currentTimeMillis() - playerMap.get(playersInNether.get(i)) > DeityNether.plugin.getNetherTimeLimit()) {
				WorldHelper.removePlayer(playersInNether.get(i));
				NetherSQL.removePlayer();
			}
		}
		
	}
	
	public static void addPlayer(Player p) {
		playerMap.put(p, System.currentTimeMillis());
		playersInNether.add(p);
	}
	
	public static void removePlayer(Player p) {
		playerMap.remove(p);
		playersInNether.remove(p);
	}

}
