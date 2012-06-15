package com.imdeity.DeityKingdomsDev.DeityNether;

import org.bukkit.plugin.java.JavaPlugin;
import com.imdeity.DeityKingdomsDev.DeityNether.listeners.PigmanListener;

public class DeityNether extends JavaPlugin {
	public static int PIGMAN_DROP_GOLD_CHANCE = 10; //Chance for pigman to drop gold - 10 = 10% chance
	public static int PLAYER_JOIN_NETHER_WAIT_MINUTES = 1440;
	public static int PLAYER_JOIN_NETHER_WAIT_MILLIS;
	
	public void onEnable(){
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
		PLAYER_JOIN_NETHER_WAIT_MILLIS = PLAYER_JOIN_NETHER_WAIT_MINUTES * 60 * 1000;
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerChecker(this), 0, 100);
	}
	
	public void onDisable(){
		
	}

}
