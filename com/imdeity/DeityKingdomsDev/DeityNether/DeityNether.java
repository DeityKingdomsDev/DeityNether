package com.imdeity.DeityKingdomsDev.DeityNether;



import org.bukkit.plugin.java.JavaPlugin;

import com.imdeity.DeityKingdomsDev.DeityNether.listeners.PigmanListener;

public class DeityNether extends JavaPlugin {
	
	public void onEnable(){
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		this.getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
	}
	
	public void onDisable(){
		
	}

}
