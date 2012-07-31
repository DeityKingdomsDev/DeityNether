package com.imdeity.nether.helpers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.imdeity.nether.DeityNether;

public class WorldHelper {
	
	World w = DeityNether.plugin.getServer().getWorld(DeityNether.plugin.getNetherName());
	
	public static void addPlayer(Player p) {
		
	}
	
	public static void removePlayer(Player p) {
		
	}
	
	public static void regenerateNether() {
		
	}
	
	private void cuboid(Location corner1, Location corner2, Material m) {
		
		for(int x = corner1.getBlockX(); x < corner2.getBlockX(); x++) {
			for(int y = corner1.getBlockY(); y < corner2.getBlockY(); y++) {
				for(int z = corner1.getBlockZ(); z < corner2.getBlockZ(); z++) {
					w.getBlockAt(x, y, z).setType(m);
				}
			}
		}
		
	}
	
	

}
