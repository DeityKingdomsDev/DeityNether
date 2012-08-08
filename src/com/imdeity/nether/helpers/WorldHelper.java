package com.imdeity.nether.helpers;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.imdeity.nether.DeityNether;
import com.imdeity.nether.sql.NetherSQL;

public class WorldHelper {

	private static World w = DeityNether.plugin.getServer().getWorld(DeityNether.plugin.getNetherName());//Private since it only needs to be used in this class

	public static void addPlayer(Player p) {
		if(p.hasPermission("deity.nether.override")) {//If they are an overrider, they do not need to be added 
			p.teleport(w.getSpawnLocation());
		} else {
			p.teleport(w.getSpawnLocation());
			NetherSQL.addPlayer(p);
			PlayerChecker.addPlayer(p);
		}
	}

	public static void removePlayer(Player p) {
		if(p.hasPermission("deity.nether.override")) {//If they are an overrider, they do not need to be removed
			p.teleport(w.getSpawnLocation());
		} else {
			p.teleport(w.getSpawnLocation());
			NetherSQL.removePlayer(p);
			PlayerChecker.removePlayer(p);
		}
	}

	public static void regenerateNether() {
		NetherSQL.clearTime();
		File netherFolder = new File(DeityNether.plugin.getNetherName()); //The nether folder
		delDir(netherFolder); //Delete the nether folder

	}

	private static void delDir(File file) {
		for(File f: file.listFiles()) {
			if(f.isDirectory()) {
				delDir(f); //If the file from the file list is a directory, do this method with that file
			}else{
				f.delete(); //The file is a regular file, now delete it
			}
		}
	}

	private static void cuboid(Location corner1, Location corner2, Material m) {
		//Basic cuboid for loop trio
		for(int x = corner1.getBlockX(); x < corner2.getBlockX(); x++) {
			for(int y = corner1.getBlockY(); y < corner2.getBlockY(); y++) {
				for(int z = corner1.getBlockZ(); z < corner2.getBlockZ(); z++) {
					w.getBlockAt(x, y, z).setType(m);
				}
			}
		}

	}

	public static void addNetherSpawn() {
		//We go through the nether looking for a usable spawn location. We start at 10 because all blocks below that are lava and netherrack and bedrock
		for(int i = 10; i < 127; i++) {
			if(w.getBlockAt(0, i, 0).getType() == Material.AIR) {
				w.setSpawnLocation(0, i, 0); //We found a good spawn location
				Location corner1 = new Location(w, -5, i, -5);
				Location corner2 = new Location(w, 5, i, 5);
				cuboid(corner1, corner2, Material.NETHERRACK); //We make a platform of netherrack below spawn incase the spawn is above a lava lake
				return; //Exit from the method, we are done
			}
		}
		//We have reached the end of the for loop, we never found a good spawn location
		//So now we make a cobblestone platform and a box of air above it
		Location corner1 = new Location(w, -5, 64, -5);
		Location corner2 = new Location(w, 5, 64, 5);
		Location corner3 = new Location(w, 5, 74, 5);
		cuboid(corner1, corner3, Material.AIR);
		cuboid(corner1, corner2, Material.COBBLESTONE);

	}
}
