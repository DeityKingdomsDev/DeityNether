package com.imdeity.nether.helpers;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;

public class WorldHelper {
	private static DeityNether plugin;
	
	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}
	
	static Location l;
//	static File file;
//	static File newFile;
//	static File fileOld;
	static World world;
	static Calendar cal = new GregorianCalendar();
	static WorldCreator worldCreator;
	public static void removePlayer(Player p) {
		//TODO Add code to move player to the main server
		//Move the player back to the over world
		l = new Location(plugin.getServer().getWorld("world"), plugin.getServer().getWorld("world").getSpawnLocation().getX(),plugin.getServer().getWorld("world").getSpawnLocation().getY(),plugin.getServer().getWorld("world").getSpawnLocation().getZ()); 
		p.teleport(l);
	}
	
	public static void addPlayer(Player p) {
		//TODO Add code to move player to cloud server
			l = new Location(plugin.getServer().getWorld("world_nether"), plugin.getServer().getWorld("world_nether").getSpawnLocation().getX(), plugin.getServer().getWorld("world_nether").getSpawnLocation().getY(), plugin.getServer().getWorld("world_nether").getSpawnLocation().getZ()); 
			p.teleport(l);
	}
	public static void regenerateNether
		boolean success  = (new File("world_nether")).delete();
		if(!sucess) {
			getLogger().info("[DeityNether] Nether file deletion failed!");
		}
	}
	
	/*public static void regenerateNether() {
		world = Bukkit.getWorld("world_nether");
		//TODO Make this the actual world size
		for(int x = -10; x<10; x++) {
			for(int y = -10; y<10; y++) {
				if(world.regenerateChunk(x,y)) System.out.println("[DeityNether] Regenerated nether chunk at: "+x+"x "+y+"y");
			}
		}
		
		
//		file = new File("world_nether");
//		newFile = new File("world_nether_new");
//		newFile.mkdirs();
//		fileOld = new File("world_nether_old");
//		Bukkit.unloadWorld("world_nether", false);
//		file.renameTo(fileOld);
//		success = newFile.renameTo(new File("world_nether"));
//		worldCreator = new WorldCreator("world_nether");
//		worldCreator.environment(Environment.NETHER);
//		worldCreator.createWorld();
//		System.out.println(file.delete());
//		DeityNether.config.set("last-reset", Long.valueOf(System.currentTimeMillis()));
//		if(success) System.out.println("[DeityNether] The nether has been reset!");
//		else System.out.println("[DeityNether] Nether reset failed! :(")
	}*/
}
