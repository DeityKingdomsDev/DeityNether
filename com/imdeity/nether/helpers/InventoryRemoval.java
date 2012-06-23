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
			hasGold = false;
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

		if(verification == 36 && hasGold == true) {
			return true;
		}else{
			inv.addItem(gold);
			p.sendMessage(ChatColor.RED + "You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
		}

		return false;
	}

	//mbona's code:
	/*
	private static DeityNether plugin;
	Player player;
	Tp tp = movePlayer(player);
	ItemStack item = inventory.getContents();
	Gold gold = Material.GOLD_INGOT;
	for(int i = 0; i<items.length(); i++) {
		if (item[i].getType==Material.WOOD_SWORD) {
			tp=true;
		}
		else if (item[i].getType==Material.STONE_SWORD) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_SWORD) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_SWORD) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_SWORD) {
			tp=true;
		}
		else if (item[i].getType==Material.WOOD_SPADE) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_SPADE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_SPADE) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_SPADE) {
			tp=true;
		}
		else if (item[i].getType==Material.WOOD_PICKAXE) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_PICKAXE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_PICKAXE) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_PICKAXE) {
			tp=true;
		}
		else if (item[i].getType==Material.WOOD_AXE) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_AXE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_AXE) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_AXE) {
			tp=true;
		}
		else if (item[i].getType==Material.WOOD_HOE) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_HOE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_HOE) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_HOE) {
			tp=true;
		}
		else if (item[i].getType==Material.LEATHER_HELMET) {
			tp=true;
		}
		else if (item[i].getType==Material.LEATHER_CHESTPLATE) {
			tp=true;
		}
		else if (item[i].getType==Material.LEATHER_LEGGINGS) {
			tp=true;
		}
		else if (item[i].getType==Material.LEATHER_BOOTS) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_HELMET) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_CHESTPLATE) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_LEGGINGS) {
			tp=true;
		}
		else if (item[i].getType==Material.IRON_BOOTS) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_HELMET) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_CHESTPLATE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_LEGGINGS) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLD_BOOTS) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_HELMET) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_CHESTPLATE) {
			tp=true;
		}
		else if (item[i].getType==Material.DIAMOND_LEGGINGS) {
			tp=true;
		}
		else if (item[i].getType==Material.BREAD) {
			tp=true;
		}
		else if (item[i].getType==Material.CAKE) {
			tp=true;
		}
		else if (item[i].getType==Material.COOKIE) {
			tp=true;
		}
		else if (item[i].getType==Material.MELON) {
			tp=true;
		}
		else if (item[i].getType==Material.MUSHROOM_SOUP) {
			tp=true;
		}
		else if (item[i].getType==Material.RAW_CHICKEN) {
			tp=true;
		}
		else if (item[i].getType==Material.COOKED_CHICKEN) {
			tp=true;
		}
		else if (item[i].getType==Material.RAW_BEEF) {
			tp=true;
		}
		else if (item[i].getType==Material.COOKED_BEEF) {
			tp=true;
		}
		else if (item[i].getType==Material.PORK) { //I couldn't find RAW_PORK
			tp=true;
		}
		else if (item[i].getType==Material.RAW_FISH) {
			tp=true;
		}
		else if (item[i].getType==Material.COOKED_FISH) {
			tp=true;
		}
		else if (item[i].getType==Material.APPLE) {
			tp=true;
		}
		else if (item[i].getType==Material.GOLDEN_APPLE) {
			tp=true;
		}
		else if (item[i].getType==Material.ROTTEN_FLESH) {
			tp=true;
		}
		else if (item[i].getType=Material.SPIDER_EYE) {
			tp=true;
		}
		else if(items[1].getAmount()==0) {
			tp=true;
		}
		else {
			tp=false;
			player.sendMessage(ChatColor.RED + "Please remove the blacklisted items from you inventory.");
		}
	}
	if (inventory.contains(gold)) {
		inventory.remove(gold);
		tp=true;
	}
	 */
}

