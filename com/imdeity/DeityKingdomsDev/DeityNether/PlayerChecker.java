package com.imdeity.DeityKingdomsDev.DeityNether;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.SQL.NetherSQL;
import com.imdeity.DeityKingdomsDev.DeityNether.helpers.WorldHelper;

public class PlayerChecker implements Runnable {
	private final DeityNether plugin;
	Player player;
	long currentTime;
	
	public static HashMap<Player, Long> map = new HashMap<Player, Long>();
	public static ArrayList<Player> playersInNether = new ArrayList<Player>();
	
	public PlayerChecker(DeityNether plugin){
		this.plugin = plugin;
	}
	@Override
	public void run() {
		System.out.println("Running " + playersInNether.size());
		for(int i = 0; i < playersInNether.size(); i++){
			currentTime = System.currentTimeMillis();
			player = playersInNether.get(i);
			System.out.println(currentTime - map.get(player) + " > " + DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS);
			if((currentTime - map.get(player)) > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
				System.out.println("REMOVING PLAYER");
				WorldHelper.removePlayer(player);
				NetherSQL.removePlayer(player);
			}
		}

	}

}
