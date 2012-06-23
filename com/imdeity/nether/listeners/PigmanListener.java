package com.imdeity.nether.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import com.imdeity.nether.*;


public class PigmanListener implements Listener {
	private final DeityNether plugin;
	Entity entity;
	int random;
	int chance = DeityNether.PIGMAN_DROP_GOLD_CHANCE;
	int amountDropped;
	ItemStack drops;
	ItemStack newDrop = new ItemStack(Material.GOLD_NUGGET, 1);
	World world;
	String s;

	public PigmanListener(DeityNether plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void onPigmanDeath(EntityDeathEvent event){
		entity = event.getEntity();
		world = entity.getWorld();
		s = world.getName();
		if(entity instanceof PigZombie){
			random = (int)(Math.random()*(100 - chance + 1) + chance);
			if(random < chance){
				Bukkit.getServer().getWorld(s).dropItem(entity.getLocation(), newDrop);
			}
		}
	}
	@EventHandler
	public void onPigmanDrop(ItemSpawnEvent event2){
		entity = event2.getEntity();
		if(entity instanceof PigZombie){
			event2.setCancelled(true);
		}
	}

}
