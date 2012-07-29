package com.imdeity.nether.helpers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;
import com.imdeity.nether.sql.NetherSQL;

public class WorldHelper {
	private final DeityNether plugin;
	private Util util = new Util();
	ArrayList<Player> triedToLeave = new ArrayList<Player>();
	public World world;

	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}

	public void addPlayer(Player p) throws SQLException {
		if(p.getWorld() == plugin.getServer().getWorld("world_nether")){ p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "You're already in the nether."); return; }
		checkNetherSpawn();
		if(p.hasPermission("Deity.nether.override") || p.isOp()){
			if(InventoryRemoval.checkInventory(p)){
				p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
				p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome to the nether! You will have no time limit.");
				NetherSQL.addPlayer(p);
			}else{
				p.sendMessage(ChatColor.RED + "[DeityNether] You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
			}
		} else if(p.hasPermission("Deity.nether.general")){
			if(util.playerHasWaited(p)) {
				if(InventoryRemoval.checkInventory(p)){
					p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
					if((plugin.NETHER_TIME_LIMIT_MINUTES/60) == 1){
						p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome to the nether! You will have " + ChatColor.GREEN + "1 " + ChatColor.BLUE + "hour in the nether.");
					}else{
						p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome to the nether! You will have " + ChatColor.GREEN + plugin.NETHER_TIME_LIMIT_MINUTES/60 + ChatColor.BLUE + "hours in the nether.");
					}
					NetherSQL.addPlayer(p);
					PlayerChecker.playersInNether.add(p);
					PlayerChecker.map.put(p, System.currentTimeMillis());
				}else{
					p.sendMessage(ChatColor.RED + "[DeityNether] You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
				}
			} else {
				p.sendMessage(ChatColor.RED + "[DeityNether] You must wait " + ChatColor.GREEN + DeityNether.PLAYER_JOIN_NETHER_WAIT_HOURS + ChatColor.RED + " hours before entering the nether again!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "[DeityNether] You do not have permission to use that command!");
		}
		//TODO: Move player back to main server
	}

	public void removePlayer(Player p) {
		if(p.getWorld() == plugin.getServer().getWorld("world")){ p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "You're not in the nether."); return;}
		if(p.hasPermission("Deity.nether.override") || p.isOp()){
			p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
		}else if(p.hasPermission("Deity.nether.general")){
			if(!triedToLeave.contains(p)){
				p.sendMessage(ChatColor.RED + "[DeityNether]" + ChatColor.GREEN + " Are you sure you want to leave? You still have " + ChatColor.BLUE + (DeityNether.NETHER_TIME_LIMIT_MINUTES - (((System.currentTimeMillis() - PlayerChecker.map.get(p))/1000)/60)) + ChatColor.GREEN + " minutes left in the nether! Do " + ChatColor.BLUE + "/nether leave" + ChatColor.GREEN + " again if you really want to leave.");
				triedToLeave.add(p);
			}else{
				p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
				triedToLeave.remove(p);
				NetherSQL.removePlayer(p);
				PlayerChecker.playersInNether.remove(p);
				PlayerChecker.map.remove(p);
				p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome to the main world!");
			}
		}else{
			p.sendMessage(ChatColor.RED + "[DeityNether] You do not have permission to use that command!");
		}
		//TODO: Move player to cloud server
	}


	public void regenerateNether() {
		boolean success = delDir(new File("world_nether"));
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
	public boolean delDir(File file) {
		File[] fileList = file.listFiles();
		for(int i = 0; i<fileList.length; i++) {
			if(fileList[i].isDirectory()) {
				delDir(fileList[i]);
			}
			else {
				fileList[i].delete();
			}
		}
		plugin.config.set("nether-spawn-needed", true);
		return file.delete();
	}

	public void checkNetherSpawn() {
		world = DeityNether.plugin.getServer().getWorld("world_nether");

		if(plugin.config.getBoolean("nether-spawn-needed")){
			for(int i = 10; i < 127; i++){
				if(plugin.getServer().getWorld("world_nether").getBlockAt(0, i, 0).getTypeId() == 0){
					makeNetherrackPlatform(new Location(world, 0, i, 0));
					world.setSpawnLocation(0, i, 0);
					System.out.println("Nether spawn set at " + 0 + " " + i + " " + 0);
					return;
				}
			}
			makeSpawnBox(world.getSpawnLocation());
		}
	}
	
	private void makeNetherrackPlatform(Location loc){
		System.out.println("Making netherrack platform...");
		for(int x = loc.getBlockX() - 5; x < loc.getBlockX() + 5; x++){
			for(int y = loc.getBlockY(); y < loc.getBlockY(); y++){
				for(int z = loc.getBlockZ() + 5; z < loc.getBlockZ() + 5; z++){
					world.getBlockAt(x, y, z).setType(Material.NETHERRACK);
				}
			}
		}
	}
	
	private void makeSpawnBox(Location loc){
		System.out.println("Making spawn box");
		for(int x = loc.getBlockX() - 5; x < loc.getBlockX() + 5; x++){
			for(int y = loc.getBlockY(); y < loc.getBlockY(); y++){
				for(int z = loc.getBlockZ() + 5; z < loc.getBlockZ() + 5; z++){
					world.getBlockAt(x, y, z).setType(Material.COBBLESTONE);
				}
			}
		}
		
		for(int x = loc.getBlockX() - 5; x < loc.getBlockX() + 5; x++){
			for(int y = loc.getBlockY() + 1; y < loc.getBlockY() + 10; y++){
				for(int z = loc.getBlockZ() - 5; z < loc.getBlockZ() + 5; z++){
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
		world.setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		System.out.println("Nether spawn set at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
	}
}