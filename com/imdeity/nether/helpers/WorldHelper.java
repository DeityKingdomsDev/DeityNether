package com.imdeity.nether.helpers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.imdeity.nether.*;
import com.imdeity.nether.sql.NetherSQL;

public class WorldHelper {
	private final DeityNether plugin;
	private Util util = new Util();
	ArrayList<Player> triedToLeave = new ArrayList<Player>();


	public WorldHelper(DeityNether plugin){
		this.plugin = plugin;
	}

	public void addPlayer(Player p) throws SQLException {
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
					if((plugin.NETHER_TIME_LIMIT_MINUTES/60) ==1){
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
		if(p.hasPermission("Deity.nether.override") || p.isOp()){
			p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
		}else if(p.hasPermission("Deity.nether.general")){
			if(!triedToLeave.contains(p)){
				p.sendMessage(ChatColor.RED + "[DeityNether]" + ChatColor.GREEN + " Are you sure you want to leave? You still have " + ChatColor.BLUE + (DeityNether.NETHER_TIME_LIMIT_MINUTES - (((System.currentTimeMillis() - PlayerChecker.map.get(p))/1000)/60)) + ChatColor.GREEN + " minutes left in the nether! Do " + ChatColor.BLUE + "/nether leave" + ChatColor.GREEN + " again if you really want to leave.");
				triedToLeave.add(p);
			}else{
				p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
				triedToLeave.remove(p);
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

	public void checkNetherSpawn() {
		if(plugin.netherNeedsPlatform){
			for(int i = 127; i > 0; i--) {
				if(plugin.getServer().getWorld("world_nether").getBlockAt(0, i, 0).getTypeId() == 0){
					Location one = new Location(plugin.getServer().getWorld("world_nether"), -5, i, -5);
					Location two = new Location(plugin.getServer().getWorld("world_nether"), 5, i, 5);

					for(int k = one.getBlockX(); k < two.getBlockX(); k++){
						for(int n = one.getBlockY(); n < two.getBlockY(); n++){
							for(int m = one.getBlockZ(); m < two.getBlockZ(); m++){
								plugin.getServer().getWorld("world_nether").getBlockAt(k, n, m).setTypeId(Material.NETHERRACK.getId());
							}
						}
					}
				}
				return;
			}

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
	}
}