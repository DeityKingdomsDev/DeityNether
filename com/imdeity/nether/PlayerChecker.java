package com.imdeity.nether;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.imdeity.nether.sql.NetherSQL;
import com.imdeity.nether.helpers.*;

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
		for(int i = 0; i < playersInNether.size(); i++){
			currentTime = System.currentTimeMillis();
			player = playersInNether.get(i);
			if((currentTime - map.get(player)) > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
				WorldHelper.removePlayer(player);
				NetherSQL.removePlayer(player);
				player.sendMessage(ChatColor.RED + "Your time in the nether is up! You can revisit in 24 hours.");
			}
		}

	}

}
