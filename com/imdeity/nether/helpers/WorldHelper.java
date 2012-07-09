package com.imdeity.nether.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;

public class WorldHelper {
	private final DeityNether plugin;

	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}

	static Location l;
	static World world;
	static Calendar cal = new GregorianCalendar();
	static WorldCreator worldCreator;
	File netherFolder = new File("world_nether");

	public void removePlayer(Player p) {
		//TODO: Move player back to main server
		p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
	}

	public void addPlayer(Player p) {
		if(plugin.netherNeedsPlatform){
			Location l = plugin.getServer().getWorld("world_nether").getSpawnLocation();
			int x = l.getBlockX();
			int y = l.getBlockY();
			int z = l.getBlockZ();
			Location first = new Location(plugin.getServer().getWorld("world_nether"), x-5, y, z-5);
			Location second = new Location(plugin.getServer().getWorld("world_nether"), x+5, y, z+5);
			for(int i = first.getBlockX(); i < second.getBlockX(); i++){
				for(int j = first.getBlockZ(); j < second.getBlockZ(); j++){
					plugin.getServer().getWorld("world_nether").getBlockAt(i, y, j).setTypeId(4);
				}
			}
			Location low = new Location(plugin.getServer().getWorld("world_nether"), first.getBlockX(), y+1, first.getBlockZ());
			Location high = new Location(plugin.getServer().getWorld("world_nether"), second.getBlockX(), y+10, second.getBlockZ());
			for(int k = low.getBlockX(); k < high.getBlockX(); k++){
				for(int n = low.getBlockY(); n < high.getBlockY(); n++){
					for(int m = low.getBlockZ(); m < high.getBlockZ(); m++){
						plugin.getServer().getWorld("world_nether").getBlockAt(k, n, m).setTypeId(0);
					}
				}
			}
		}
		plugin.netherNeedsPlatform = false;
		//TODO: Move player to cloud server
		p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
	}
	public void regenerateNether() {
		if(!netherFolder.exists()){
			boolean success = delDir(netherFolder);
			if(!success) {
				System.out.println("[DeityNether] Nether file deletion failed!");
				return;
			}

			DeityNether.config.set("last-reset", Long.valueOf(System.currentTimeMillis()));
			try {
				DeityNether.config.save(DeityNether.configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean delDir(File file) {
		File[] fileList = file.listFiles();
		for(int i = 0; i<fileList.length; i++) {
			if(fileList[i].isDirectory()) {
				delDir(fileList[i]);
			}
			else {
				fileList[i].delete();
			}
		}
		return file.delete();
	}

}
