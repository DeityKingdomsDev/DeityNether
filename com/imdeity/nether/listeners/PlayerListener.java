package com.imdeity.nether.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.imdeity.nether.DeityNether;
import com.imdeity.nether.sql.NetherSQL;

public class PlayerListener implements Listener {
	private final DeityNether plugin;
	
	public PlayerListener(DeityNether plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(player.getWorld().getName().equals("world_nether")) {
			NetherSQL.removePlayer(player);
			player.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
		}
	}
}
