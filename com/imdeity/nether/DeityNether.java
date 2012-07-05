package com.imdeity.nether;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.imdeity.nether.sql.NetherSQL;
import com.imdeity.nether.helpers.*;
import com.imdeity.nether.listeners.PigmanListener;
import com.imdeity.nether.listeners.PlayerDeathListener;
import com.imdeity.nether.listeners.PlayerListener;

public class DeityNether extends JavaPlugin {
	public static int PIGMAN_DROP_GOLD_CHANCE = 10; //Chance for pigman to drop gold - 10 = 10% chance
	public static int PLAYER_JOIN_NETHER_WAIT_MINUTES = 60;
	public static int PLAYER_JOIN_NETHER_WAIT_MILLIS;
	public static int WORLD_RESET_HOURS = 24;
	public static int WORLD_RESET_MILLIS;
	public static int GOLD_BLOCK_AMOUNT = 2; //Amount of gold blocks to charge for entry
	public static int TIME_LEFT;
	NetherSQL nsql;
	private WorldHelper wh;
	long lastReset;
	
	public static FileConfiguration config;
	File configFile;
	
	@Override
	public void onEnable(){
		wh = new WorldHelper(this);
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		PLAYER_JOIN_NETHER_WAIT_MILLIS = PLAYER_JOIN_NETHER_WAIT_MINUTES * 60 * 1000;
		TIME_LEFT = //TODO: Get time left.
		WORLD_RESET_MILLIS = WORLD_RESET_HOURS * 60 * 60 * 1000;
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerChecker(this), 0, 100);
		
		try {
			configFile = new File(getDataFolder(), "config.yml");
			config = new YamlConfiguration();
			
			if(!configFile.exists()){
				configFile.getParentFile().mkdirs();
			}
			
			config.load(configFile);
		} catch (Exception e) {
			System.out.println("[DeityNether] Config loaded!");
		}
		
		if(!config.contains("last-reset")){
			config.set("last-reset", Long.valueOf(System.currentTimeMillis()));
			lastReset = System.currentTimeMillis();
			
		} else {
			lastReset = config.getLong("last-reset");
		}
		if((System.currentTimeMillis() - lastReset) < WORLD_RESET_MILLIS){
			
			wh.regenerateNether();
		}
		
		NetherSQL.checkTables();
		
		nsql = new NetherSQL();
		
	}
	
	public void onDisable(){
		try {
		config.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
