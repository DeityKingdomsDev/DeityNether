package com.imdeity.DeityKingdomsDev.DeityNether;

import org.bukkit.plugin.java.JavaPlugin;
import com.imdeity.DeityKingdomsDev.DeityNether.listeners.PigmanListener;

public class DeityNether extends JavaPlugin {
	public static int PIGMAN_DROP_GOLD_CHANCE = 10; //Chance for pigman to drop gold - 10 = 10% chance
	
	public void onEnable(){
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
	}
	
	public void onDisable(){
		
	}

}
