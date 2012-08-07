package com.imdeity.nether.helpers;

import java.util.ListIterator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.imdeity.deityapi.object.InventoryObject;
//268, 269, 270, 271, 290, 272, 273, 274, 275, 291, 256, 257, 258, 267, 292, 283, 284, 285, 286, 294, 276, 277, 278, 279, 293, 261, 262, //tools, swords, weapons
//298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, //all armor
//260, 282, 319, 320, 322, 349, 350, 354, 357, 363, 364, 365, 366, 367, 382
import com.imdeity.nether.DeityNether;

public class PlayerInventoryChecker extends InventoryObject{
	static int goldBlockPayment = DeityNether.plugin.getGoldBlockPrice();
	
	public static boolean checkInventory(Player player) {
		Inventory I;
		ListIterator<ItemStack> iterator;
		int value;
		boolean check = true;
		boolean endLoop = false;
		I = player.getInventory();
		iterator = I.iterator();
		while(iterator.hasNext() && check) { //cycle through items in inventory
			value = iterator.next().getTypeId(); //cycle to next inventory slot
			while(!endLoop) {
				if(value>=267 && value<=286) { //check if item is between item ids
					endLoop=true;
				}
				else if(value>=297 && value<=317) {
					endLoop=true;
				}
				else if(value>=319 && value<=320) {
					endLoop=true;
				}
				else if(value==322) {
					endLoop=true;
				}
				else if(value>=349 && value<=350) {
					endLoop=true;
				}
				else if(value>=363 && value<=367) {
					endLoop=true;
				}
				else if(value>=261 && value<=262) {
					endLoop=true;
				}
				else if(value>=256 && value<=258) {
					endLoop=true;
				}
				check=false;
			}
			
		}
		if(check) return Test(I);
		return false;
	}
	@SuppressWarnings("unused")
	public static boolean Test(Inventory I) { //remove gold
		boolean check = false;
		if(check) {
			int goldBlocks = ((InventoryObject) I).amountOfItemsInInventory(I, new ItemStack(Material.GOLD_BLOCK));
			if(goldBlocks<goldBlockPayment) check=false;
			I.remove(new ItemStack(Material.GOLD_BLOCK));
			PlayerInventoryChecker checker = new PlayerInventoryChecker();
			ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
			stack.setAmount(PlayerInventoryChecker.goldBlockPayment);
			I.addItem(stack);
		}
		return check;
	}
}
