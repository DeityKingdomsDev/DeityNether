package com.imdeity.nether;

import java.util.Timer;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.nether.cmds.*;
import com.imdeity.nether.helpers.*;
import com.imdeity.nether.listeners.*;

public class DeityNether extends DeityPlugin {

	public static DeityNether plugin;
	public static NetherSpawnChecker netherSpawnChecker;
	Timer timer = new Timer("NetherSpawnChecker");

	@Override
	protected void initCmds() {
		//cmd handler for all commands
		this.registerCommand(new CmdHandler("DeityNether"));

	}

	@Override
	protected void initConfig() {
		//useful config values to use in all classes
		this.config.addDefaultConfigValue("world-name", "world");
		this.config.addDefaultConfigValue("nether-name", "world_nether");
		this.config.addDefaultConfigValue("gold-block-price", 2);
		this.config.addDefaultConfigValue("nether-time-limit-minutes", 60);
		this.config.addDefaultConfigValue("pigman-drop-gold-chance", 0.1D);
		this.config.addDefaultConfigValue("nether-reset-hours", 24);
		this.config.addDefaultConfigValue("nether-needs-spawn", false);
		this.config.addDefaultConfigValue("last-reset", System.currentTimeMillis());

	}

	@Override
	protected void initDatabase() {
		//table for logging actions
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `deity_nether_action_log` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`action_type` CHAR( 1 ) NOT NULL ,"+
				"`time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"+
				") ENGINE = MYISAM COMMENT =  'Nether action log for records of player joins/leaves';");

		//table for logging player's time in nether
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `deity_nether_stats` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`time_in_nether` INT( 4 ) NOT NULL DEFAULT  '0' COMMENT  'time in seconds',"+
				"UNIQUE (`player_name`)"+
				") ENGINE = MYISAM COMMENT =  'Current time in nether record for players';");

	}

	@Override
	protected void initInternalDatamembers() {
		if((System.currentTimeMillis() - config.getLong("last-reset") > (config.getLong("nether-reset-hours") * 60 * 60 * 1000))) {
			config.set("last-reset", System.currentTimeMillis());
			config.set("nether-needs-spawn", true);
			WorldHelper.regenerateNether();
		}
		
	}

	@Override
	protected void initLanguage() {
		//no language yml
	}

	@Override
	protected void initListeners() {
		this.registerListener(new PlayerListener()); //listening for player death and logout
		this.registerListener(new PigmanListener()); //listening for pigman death to modify drops

	}

	@Override
	protected void initPlugin() {
		plugin = this; //used fo referencing the bukkit api from other classes via DeityNether.plugin
	}

	@Override
	protected void initTasks() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerChecker(), 0, 20 * 60); //Will check if a player's time is up every minute
		timer.schedule(new NetherSpawnChecker(), 0, 100); //We schedule a timertask so we can cancel the task once we generate the nether spawn

	}

	//used for getting config values
	public String getWorldName() {
		return plugin.config.getString("world-name");
	}

	public String getNetherName() {
		return plugin.config.getString("nether-name");
	}

	public int getGoldBlockPrice() {
		return plugin.config.getInt("gold-block-price");
	}

	public int getNetherTimeLimit() {
		return plugin.config.getInt("nether-time-limit-minutes");
	}

	public double getPigmanDropGoldChance() {
		return plugin.config.getDouble("pigman-drop-gold-chance");
	}

	public int getWorldResetHours() {
		return plugin.config.getInt("world-reset-hours");
	}
	
	public boolean netherNeedsSpawn() {
		return plugin.config.getBoolean("nether-needs-spawn");
	}
}
