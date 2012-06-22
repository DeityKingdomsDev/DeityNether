package com.imdeity.DeityKingdomsDev.DeityNether.helpers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.DeityNether;
import com.imdeity.DeityKingdomsDev.DeityNether.helpers.WorldHelper;

public class InventoryRemoval {
	private static DeityNether plugin;
	public static int GOLD_TO_REMOVE = 2;//Gold is now a constant so it can be changed easier
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
		else if (item[i].getType==Material.SPIDER_EYE) {
			tp=true;
		}
		else if(items[i].getAmount()==0) {
			tp=true;
		}
		else {
			tp=false;
			player.sendMessage(ChatColor.RED + "Please remove the blacklisted item(s) from you inventory.");
		}
	}
	if (inventory.contains(gold)) {
		inventory.remove(gold, GOLD_TO_REMOVE);
		tp=true;
	}
	else {
		tp=false;
		player.sendMessage(ChatColor.RED + "Please bring" + GOLD_TO_REMOVE + "gold to enter the nether.");
	}
}
