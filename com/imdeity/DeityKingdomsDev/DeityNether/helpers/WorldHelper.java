package com.imdeity.DeityKingdomsDev.DeityNether.helpers;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.DeityNether;

public class WorldHelper {
	private static DeityNether plugin;
	
	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}
	
	static Location l;
	static File file;
	static File newFile;
	static boolean success;
	
	static WorldCreator worldCreator;

	public static void removePlayer(Player p) {
		//TODO Add code to move player to the main server
		//Move the player back to the over world
		l = new Location(p.getWorld(), 100, 65, 100); //Set 65 to whatever is needed for the main spawn
		p.teleport(l);
	}
	
	public static void addPlayer(Player p) {
		//TODO Add code to move player to cloud server
		//Move the player to the nether
		l = new Location(p.getWorld(), 0, 65, 0); //Set 65 to whatever is needed for the nether spawn
		p.teleport(l);
	}

	public static void regenerateNether() {
		file = new File("world_nether");
		newFile = new File("plugins" + File.pathSeparator + "DeityNether" + File.pathSeparator + "world_nether " + System.currentTimeMillis());
		newFile.mkdirs();
		
		success = file.renameTo(newFile);
		
		plugin.getServer().createWorld(worldCreator);
	}

}
