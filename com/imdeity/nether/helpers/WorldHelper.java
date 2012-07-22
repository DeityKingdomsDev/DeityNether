package com.imdeity.nether.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;

public class WorldHelper {
	private final DeityNether plugin;

	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}

	Location l;
	static World world;
	static WorldCreator worldCreator;
	File netherFolder = new File("world_nether");
	int y;

	public void removePlayer(Player p) {
		//TODO: Move player back to main server
		p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
	}

	public void addPlayer(Player p) {
		if(plugin.netherNeedsPlatform){
			addNetherSpawn();
		}
		plugin.netherNeedsPlatform = false;
		//TODO: Move player to cloud server
		p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
	}
	private void addNetherSpawn() {
		for(int i = 127; i < 127; i++) {
			if(plugin.getServer().getWorld("world_nether").getBlockAt(0, i, 0).getTypeId() == 0) {
				plugin.getServer().getWorld("world_nether").setSpawnLocation(0, i, 0);
				System.out.println(i);
				
				LocalWorld world = new BukkitWorld(plugin.getServer().getWorld("world_nether"));
				EditSession e = new EditSession(world, 3000);
				Vector v1 = new Vector().setX(-5).setY(i-1).setZ(-5);
				Vector v2 = new Vector().setX(5).setY(i-1).setZ(5);
				CuboidRegion region = new CuboidRegion(world, v1, v2);
				Pattern p = new SingleBlockPattern(new BaseBlock(Material.NETHERRACK.getId()));
				try {
					e.setBlocks(region, p);
				} catch (MaxChangedBlocksException e1) {
					e1.printStackTrace();
				}
				return;
			}
		}
		
		l = new Location(plugin.getServer().getWorld("world_nether"), 0, 64, 0);
		
		LocalWorld world = new BukkitWorld(plugin.getServer().getWorld("world_nether"));
		EditSession e = new EditSession(world, 3000);
		Vector v1 = new Vector().setX(l.getBlockX() - 5).setY(l.getBlockY()).setZ(l.getBlockZ() -5);
		Vector v2 = new Vector().setX(l.getBlockX() + 5).setY(l.getBlockY()).setZ(l.getBlockZ() + 5);
		CuboidRegion region = new CuboidRegion(world, v1, v2);
		Pattern p = new SingleBlockPattern(new BaseBlock(4));
		try {
			e.setBlocks(region, p);
		} catch (MaxChangedBlocksException e1) {
			e1.printStackTrace();
		}

		EditSession e1 = new EditSession(world, 3000);
		Vector vector1 = new Vector().setX(l.getBlockX() - 5).setY(l.getBlockY() + 1).setZ(l.getBlockZ() -5);
		Vector vector2 = new Vector().setX(l.getBlockX() + 5).setY(l.getBlockY() + 10).setZ(l.getBlockZ() + 5);
		CuboidRegion region2 = new CuboidRegion(world, vector1, vector2);
		Pattern p1 = new SingleBlockPattern(new BaseBlock(0));
		try {
			e1.setBlocks(region2, p);
		} catch (MaxChangedBlocksException exception){
			exception.printStackTrace();
		}
		System.out.println(l.getBlockY());

	}

	public void regenerateNether() {
		if(netherFolder.exists()){
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
			plugin.netherNeedsPlatform = true;
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
