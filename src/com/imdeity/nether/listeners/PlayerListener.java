package com.imdeity.nether.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.imdeity.deityapi.api.DeityListener;
import com.imdeity.nether.helpers.WorldHelper;

public class PlayerListener extends DeityListener implements Listener {
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		if(event.getEntity().getWorld().getName().equalsIgnoreCase("world_nether")) WorldHelper.removePlayer(event.getEntity());  //remove dead player
	}
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		if(event.getPlayer().getWorld().getName().equalsIgnoreCase("world_nether")) WorldHelper.removePlayer(event.getPlayer());
	}
}
