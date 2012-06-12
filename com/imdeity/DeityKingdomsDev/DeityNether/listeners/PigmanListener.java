package com.imdeity.DeityKingdomsDev.DeityNether.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.imdeity.DeityKingdomsDev.DeityNether.DeityNether;


public class PigmanListener implements Listener {
	private final DeityNether plugin;
	
	public PigmanListener(DeityNether plugin){
		this.plugin = plugin;
	}
	
	public void onPigmanDeath(EntityDeathEvent event){
		
	}

}
