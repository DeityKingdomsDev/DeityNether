/*This portion of the plugin is designed so when players join the cloud server it will
 * check the players  inventory for the gold, then kick the player (//TODO there to add the send back to main)
 * if they do not have the gold, or if they have any banned items.  
 */ 
package com.imdeity.DeityKingdomsDev.DeityNether.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

import com.imdeity.DeityKingdomsDev.DeityNether.DeityNether;

public class InventoryRemoval {
	private static DeityNether plugin; 
   	int c = 1; //TODO move this to the main class
	int amount;
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		Item gold = new Item (Material.GOLD_BLOCK, 2*c); 
		Item stone = new Item (Material.STONE, amount>0);
		Item grass = new Item (Material.GRASS, amount>0);
		Item dirt = new Item (Material.DIRT, amount>0);
		Item cobblestone = new Item (Material.COBBLESTONE, amount>0);
		Item planks = new Item (Material.WOOD, amount>0);
		Item saplings = new Item (Material.SAPLING, amount>0);
		Item bedrock = new Item (Material.BEDROCK, amount>0);
		Item sand = new Item (Material.SAND, amount>0);
		Item gravel = new Item (Material.GRAVEL, amount>0);
		Item gore = new Item (Material.GOLD_ORE, amount>0);
		Item iore = new Item (Material.IRON_ORE, amount>0);
		Item core = new Item (Material.COAL_ORE, amount>0);
		Item log = new Item (Material.LOG, amount>0);
		Item leaves = new Item (Material.LEAVES, amount>0);
		Item sponge = new Item (Material.SPONGE, amount>0);
		
		if(true) {
			if (inventory.contains(gold)) {
				inventory.remove(gold);
			}
		}
	}
	
}