package com.imdeity.nether.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.imdeity.deityapi.api.DeityListener;
import com.imdeity.nether.helpers.WorldHelper;

public class PlayerListener extends DeityListener {
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		WorldHelper.removePlayer(event.getEntity());  //remove dead player
	}
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		WorldHelper.removePlayer(event.getPlayer());
	}
}
