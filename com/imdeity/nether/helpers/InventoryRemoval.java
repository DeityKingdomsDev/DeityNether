package com.imdeity.nether.helpers;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;

public class InventoryRemoval {
	static Inventory inv;
	static int[] ids = {268, 269, 270, 271, 290, 272, 273, 274, 275, 291, 256, 257, 258, 267, 292, 283, 284, 285, 286, 294, 276, 277, 278, 279, 293, 261, 262, //tools, swords, weapons
		298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, //all armor
		260, 282, 319, 320, 322, 349, 350, 354, 357, 363, 364, 365, 366, 367, 382}; //food   382 - glistening melon, obtainable in the future? 
	static ArrayList<Integer> stuff = new ArrayList<Integer>();
	static int verification;
	static ItemStack gold = new ItemStack(Material.GOLD_BLOCK, DeityNether.GOLD_BLOCK_AMOUNT);
	static boolean hasGold;

	public static boolean checkInventory(Player p) {
		for(int i = 0; i < ids.length; i++){
			stuff.add(ids[i]);
		}
		verification = 0;
		hasGold = false;
		inv = p.getInventory();
		
		if(inv.contains(gold)){
			hasGold = true;
			inv.remove(gold);
		}else{
			return false;
		}

		for(int i = 0; i < inv.getSize(); i++) {
			try{
				if(stuff.contains(inv.getItem(i).getTypeId())){
					verification++;
				}else{}
			}catch (Exception e) {
				//we have encountered air in the inventory
				verification++;
			}
		}

		if(verification == 36 && hasGold) {
			return true;
		}else{
			inv.addItem(gold);
			p.sendMessage(ChatColor.RED + "[DeityNether] You may only bring tools, swords, armor, and food into the nether. ");
			p.sendMessage(ChatColor.RED + "[DeityNether] Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. ");
			p.sendMessage(ChatColor.RED + "[DeityNether] Please take all other blocks/items out of your inventory.");
		}

		return false;
	}
}

