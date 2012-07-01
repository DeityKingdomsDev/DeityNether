package com.imdeity.nether.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

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
		//TODO: Move player back to main server
		p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
	}
	
	public static void addPlayer(Player p) {
		//TODO: Move player to cloud server
			p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
	}
	public static void regenerateNether() {
		Boolean success = delDir(new File("world_nether"));
		if(success) {
			System.out.println("[DeityNether] Nether file deletion failed!");
		}
//		worldCreator = new WorldCreator("world_nether");
//		worldCreator.environment(Environment.NETHER);
//		worldCreator.seed(3490439034);
//		worldCreator.createWorld();
		DeityNether.config.set("last-reset", Long.valueOf(System.currentTimeMillis()));
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