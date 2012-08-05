package com.imdeity.nether.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.imdeity.deityapi.api.DeityListener;

public class PigmanListener extends DeityListener {
	double goldNuggetDrop = .10; //percentage
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntityType()==EntityType.PIG_ZOMBIE) {
			event.getDrops().clear();
			double random = (new Random().nextDouble());
			ItemStack flesh = new ItemStack(Material.ROTTEN_FLESH);
			ItemStack gold = new ItemStack(Material.GOLD_NUGGET);
			gold.setAmount(1);
			int AMOUNT_FLESH = 0;
			if(random>.75) AMOUNT_FLESH=2;
			else if(random>.5) AMOUNT_FLESH=1;
			flesh.setAmount(AMOUNT_FLESH);
			event.getDrops().add(flesh);
			if(random>=1-goldNuggetDrop) event.getDrops().add(gold);
		}
	}

}
