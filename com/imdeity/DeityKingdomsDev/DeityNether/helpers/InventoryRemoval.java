package com.imdeity.DeityKingdomsDev.DeityNether.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

import com.imdeity.DeityKingdomsDev.DeityNether.DeityNether;

public class InventoryRemoval {
	private static DeityNether plugin; 
}
   
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		Item gold = new Item (Material.GOLD_INGOT, 2); //I'm not sure why I am getting an error on the "new Item" 
		int amount;
		Item stone = new Item (Material.STONE, amount>0);
		
		if (inventory.contains(gold)) {
			inventory.remove(gold);
		}
	}