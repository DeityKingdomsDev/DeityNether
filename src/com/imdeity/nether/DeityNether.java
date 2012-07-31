package com.imdeity.nether;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.nether.cmds.*;
import com.imdeity.nether.helpers.*;
import com.imdeity.nether.listeners.*;

public class DeityNether extends DeityPlugin {

	public static DeityNether plugin;

	@Override
	protected void initCmds() {
		this.registerCommand(new CmdHandler("DeityNether"));

	}

	@Override
	protected void initConfig() {
		this.config.addDefaultConfigValue("world-name", "world");
		this.config.addDefaultConfigValue("nether-name", "world_nether");
		this.config.addDefaultConfigValue("gold-block-price", 2);
		this.config.addDefaultConfigValue("nether-time-limit-minutes", 60);
		this.config.addDefaultConfigValue("pigman-drop-gold-chance", 0.1D);
		this.config.addDefaultConfigValue("world-reset-hours", 24);
		
	}

	@Override
	protected void initDatabase() {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `deity_nether_action_log` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`action_type` CHAR( 1 ) NOT NULL ,"+
				"`time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"+
				") ENGINE = MYISAM COMMENT =  'Nether action log for records of player joins/leaves';");
		
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `deity_nether_stats` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`time_in_nether` INT( 4 ) NOT NULL DEFAULT  '0' COMMENT  'time in seconds',"+
				"UNIQUE (`player_name`)"+
				") ENGINE = MYISAM COMMENT =  'Current time in nether record for players';");

	}

	@Override
	protected void initInternalDatamembers() {

	}

	@Override
	protected void initLanguage() {
		
	}

	@Override
	protected void initListeners() {
		this.registerListener(new PlayerListener());
		this.registerListener(new PigmanListener());

	}

	@Override
	protected void initPlugin() {
		plugin = this;		
	}

	@Override
	protected void initTasks() {
	getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerChecker(), 0, 20 * 60); //Will check if a player's time is up

	}
	
	public static String getWorldName() {
		return plugin.config.getString("world-name");
	}
	
	public static String getNetherName() {
		return plugin.config.getString("nether-name");
	}
	
	public static int getGoldBlockPrice() {
		return plugin.config.getInt("gold-block-price");
	}
	
	public static int getNetherTimeLimit() {
		return plugin.config.getInt("nether-time-limit-minutes");
	}
	
	public static double getPigmanDropGoldChance() {
		return plugin.config.getDouble("pigman-drop-gold-chance");
	}
	
	public static int getWorldResetHours() {
		return plugin.config.getInt("world-reset-hours");
	}
}
