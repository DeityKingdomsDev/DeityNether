package com.imdeity.nether;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.imdeity.nether.sql.NetherSQL;
import com.imdeity.nether.helpers.*;

public class NetherCommand implements CommandExecutor {
	private final DeityNether plugin;
	Player player;
	long lastJoin;
	long currentTime;
	private WorldHelper wh;

	public NetherCommand(DeityNether plugin){
		this.plugin = plugin;
		wh = new WorldHelper(plugin);
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commadLabel, String[] args) {
		player = (Player) sender;
		if(args.length == 1 && args[0].equalsIgnoreCase("join")){
		  if(player.hasPermission("Deity.nether.override")){
				moveAdmin(player);
			} else if(player.hasPermission("Deity.nether.general")){
				if(playerHasWaited(player)) {
					movePlayer(player);
				} else {
					player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.GREEN + DeityNether.PLAYER_JOIN_NETHER_WAIT_MINUTES/60 + ChatColor.RED + " hours before entering the nether again!");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
				return true;
			}
		} else if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			moveMain(player);
			return true;
		} else if(args.length == 1 && args[0].equalsIgnoreCase("?")) {
			player.sendMessage(ChatColor.AQUA + "Commands:");
			player.sendMessage(ChatColor.AQUA + "+......................+");
			player.sendMessage(ChatColor.GREEN + "/nether join" + ChatColor.AQUA+ " Teleports you to the nether.");
			player.sendMessage(ChatColor.GREEN + "/nether leave" + ChatColor.AQUA + " Teleports you back to the main world.");
			return true;
		} else if(args.length == 1 && args[0].equalsIgnoreCase("regen") && player.isOp()){
			List<Entity> list =  plugin.getServer().getWorld("world_nether").getEntities();
			for(int i = 0; i < list.size(); i++){
				Entity e = list.get(i);
				if(e instanceof Player){
					e.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
				}else{
					
				}
			}
			
			plugin.config.set("last-reset", (System.currentTimeMillis() * 2));
			plugin.getServer().shutdown();
		}else{
			player.sendMessage(ChatColor.RED + "Try: " + ChatColor.GREEN + "/nether ?");

		}
		return true;
	}

	private void movePlayer(Player p) {
		if(InventoryRemoval.checkInventory(p)){
			NetherSQL.addPlayer(p);
			wh.addPlayer(p);
			p.sendMessage(ChatColor.BLUE + "Welcome to the nether!");
		}else{
			p.sendMessage(ChatColor.RED + "You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
		}
	}
	
	private void moveAdmin(Player p) {
		wh.addPlayer(p);
		p.sendMessage(ChatColor.GREEN + "Welcome to the nether, Admin!");
	}
	
	private void moveMain(Player p) {
		wh.removePlayer(p);
		if(p.hasPermission("Deity.nether.override")){
			
		}else{
			NetherSQL.removePlayer(p);
			player.sendMessage(ChatColor.BLUE + "Welcome back to the main world! You will be able to revisit the nether in 24 hours!");
		}
	}

	public boolean playerHasWaited(Player p) {
		currentTime = System.currentTimeMillis();
		lastJoin = NetherSQL.getLastJoin(p);
		if(currentTime - lastJoin > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
			return true;
		} else {
			return true;
		}

	}

}