package com.imdeity.DeityKingdomsDev.DeityNether;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.imdeity.DeityKingdomsDev.DeityNether.helpers.WorldHelper;
import com.imdeity.DeityKingdomsDev.DeityNether.listeners.PigmanListener;

public class DeityNether extends JavaPlugin {
	public static int PIGMAN_DROP_GOLD_CHANCE = 10; //Chance for pigman to drop gold - 10 = 10% chance
	public static int PLAYER_JOIN_NETHER_WAIT_MINUTES = 1440;
	public static int PLAYER_JOIN_NETHER_WAIT_MILLIS;
	public static int WORLD_RESET_HOURS = 24;
	public static int WORLD_RESET_MILLIS;
	int lastReset;
	
	FileConfiguration config;
	File configFile;
	
	public void onEnable(){
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
		PLAYER_JOIN_NETHER_WAIT_MILLIS = PLAYER_JOIN_NETHER_WAIT_MINUTES * 60 * 1000;
		WORLD_RESET_MILLIS = WORLD_RESET_HOURS * 60 * 60 * 1000;
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerChecker(this), 0, 100);
		
		configFile = new File(getDataFolder(), "config.yml");
		config = new YamlConfiguration();
		
		if(!configFile.exists()){
			configFile.getParentFile().mkdirs();
		}
		try {
			config.load(configFile);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		if(!config.contains("last-reset")){
			config.set("last-reset", (int) System.currentTimeMillis());
			lastReset = (int) System.currentTimeMillis();
		} else {
			lastReset = config.getInt("last-reset");
		}
		
		if((int) System.currentTimeMillis() - lastReset > WORLD_RESET_MILLIS){
			WorldHelper.regenerateNether();
		}
		
	}
	
	public void onDisable(){
		try {
		config.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
