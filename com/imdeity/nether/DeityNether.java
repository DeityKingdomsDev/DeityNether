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
	public static int PLAYER_JOIN_NETHER_WAIT_HOURS = 24; //How long a player must wait before joining the nether again
	public static int PLAYER_JOIN_NETHER_WAIT_MILLIS;
	public static int WORLD_RESET_HOURS = 24; //How often the nether is reset
	public static int WORLD_RESET_MILLIS;
	public static int GOLD_BLOCK_AMOUNT = 2; //Amount of gold blocks to charge for entry
	public static int NETHER_TIME_LIMIT_MINUTES = 60; //How long a player can be in the nether
	public static int NETHER_TIME_LIMIT_MILLIS;
	private WorldHelper wh;
	long lastReset;
	public static FileConfiguration config;
	public static File configFile;
	public static boolean netherNeedsPlatform = false;
	
	@Override
	public void onEnable(){
		wh = new WorldHelper(this);
		this.getCommand("nether").setExecutor(new NetherCommand(this));
		getServer().getPluginManager().registerEvents(new PigmanListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		PLAYER_JOIN_NETHER_WAIT_MILLIS = PLAYER_JOIN_NETHER_WAIT_HOURS * 60 * 60 * 1000;
		WORLD_RESET_MILLIS = WORLD_RESET_HOURS * 60 * 60 * 1000;
		NETHER_TIME_LIMIT_MILLIS = NETHER_TIME_LIMIT_MINUTES * 60 * 1000;
		
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
		if((System.currentTimeMillis() - lastReset) > WORLD_RESET_MILLIS){
			wh.regenerateNether();
		}
		
		NetherSQL.checkTables();
		
		NetherSQL nsql = new NetherSQL();
		
		try{
			if(this.getServer().getWorld("world_nether").getBlockAt(4, 64, 4).getTypeId() == 45){
				netherNeedsPlatform = false;
			}else{
				netherNeedsPlatform = true;
			}
		}catch (Exception e){
			netherNeedsPlatform = true; //Block returned null, nether is regenerating
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
