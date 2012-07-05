package com.imdeity.nether.listeners;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.imdeity.nether.DeityNether;
import com.imdeity.nether.NetherCommand;
import com.imdeity.nether.helpers.WorldHelper;
import com.imdeity.nether.sql.NetherSQL;



public class PlayerDeathListener implements Listener {
	private final DeityNether plugin;
	WorldHelper worldHelper;

	public PlayerDeathListener(DeityNether plugin){
		this.plugin = plugin;
		worldHelper = new WorldHelper(this.plugin);
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if(event.getEntity() instanceof Player && event.getEntity().getWorld().equals(plugin.getServer().getWorld("world_nether"))) {
				moveMain(plugin.getServer().getPlayer(event.getEntity().getName()));
		}
	}
	private void moveMain(Player p) {
		worldHelper.removePlayer(p);
		if(p.hasPermission("Deity.nether.override")){
			
		}else{
			NetherSQL.removePlayer(p);
			p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome back to the main world! You will be able to revisit the nether in 24 hours!");
		}
	}

}
