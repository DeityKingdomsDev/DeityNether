package com.imdeity.nether.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.imdeity.deityapi.api.DeityListener;
import com.imdeity.nether.helpers.WorldHelper;

public class PlayerListener extends DeityListener {
	public void playerDeathEvent(PlayerDeathEvent event) {
		WorldHelper.removePlayer(event.getEntity());  //remove dead player
	}
}
