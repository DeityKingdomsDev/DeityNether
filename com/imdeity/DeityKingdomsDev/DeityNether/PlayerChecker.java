package com.imdeity.DeityKingdomsDev.DeityNether;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.SQL.NetherSQL;
import com.imdeity.DeityKingdomsDev.DeityNether.helpers.WorldHelper;

public class PlayerChecker implements Runnable {
	private final DeityNether plugin;
	Player player;
	int currentTime;
	
	public static HashMap<Player, Integer> map = new HashMap<Player, Integer>();
	public static ArrayList<Player> playersInNether = new ArrayList<Player>();
	
	public PlayerChecker(DeityNether plugin){
		this.plugin = plugin;
	}
	@Override
	public void run() {
		for(int i = 0; i < playersInNether.size(); i ++){
			currentTime = (int) System.currentTimeMillis();
			player = playersInNether.get(i);
			if((map.get(player) - currentTime) > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
				WorldHelper.removePlayer(player);
				NetherSQL.removePlayer(player);
			}
		}

	}

}
