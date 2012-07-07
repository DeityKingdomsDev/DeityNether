package com.imdeity.nether.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	//	static File file;
	//	static File newFile;
	//	static File fileOld;
	static World world;
	static Calendar cal = new GregorianCalendar();
	static WorldCreator worldCreator;

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
					System.out.println(i + "  " + y + "  " + j);
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
		boolean success = delDir(new File("world_nether"));
		if(!success) {
			System.out.println("[DeityNether] Nether file deletion failed!");
			return;
		}
		//		worldCreator = new WorldCreator("world_nether");
		//		worldCreator.environment(Environment.NETHER);
		//		worldCreator.seed(3490439034);
		//		worldCreator.createWorld();
		DeityNether.config.set("last-reset", Long.valueOf(System.currentTimeMillis()));
		try {
			DeityNether.config.save(DeityNether.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//writeProperties();
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
	public static void writeProperties() {
		FileWriter file_;
		String line;
		String f = "server.properties";
		String f_ = f+"1";
		try {
			file_ = new FileWriter(f_);
			PrintWriter file = new PrintWriter(file_);
			try {
				BufferedReader fileIn = new BufferedReader(new FileReader(f));
				while((line = fileIn.readLine()) != null) {
					if(!line.contains("level-seed")) {
						file_.write(line);
					}
					else {
						file_.write("level-seed="+System.currentTimeMillis());
					}
				}
				fileIn.close();
			}
			catch(IOException E) {
				//error
				System.out.print("[DeityNether] error writing file");
			}
			file_.flush();
			file_.close();
			(new File(f)).delete();
			file.flush();
			file.close();
			new File(f_).renameTo(new File(f));
		} catch (IOException e) {}
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