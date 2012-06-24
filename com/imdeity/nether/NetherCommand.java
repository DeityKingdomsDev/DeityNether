package com.imdeity.nether;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.imdeity.nether.sql.NetherSQL;
import com.imdeity.nether.helpers.*;

public class NetherCommand implements CommandExecutor {
	private final DeityNether plugin;
	Player player;
	long lastJoin;
	long currentTime;

	public NetherCommand(DeityNether plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		player = (Player) sender;
		if(args.length == 1 && args[0].equalsIgnoreCase("join")){
		  if(player.hasPermission("Deity.nether.override")){
				moveAdmin(player);
			}else if(player.hasPermission("Deity.nether.general")){
				if(playerHasWaited(player)){
					movePlayer(player);
				} else {
					player.sendMessage(ChatColor.RED + "Error: You must wait " + ChatColor.GREEN + DeityNether.PLAYER_JOIN_NETHER_WAIT_MINUTES/60 + ChatColor.RED + " hours before entering the nether again.");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
			}
		}else{
			player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GREEN + "/nether join");
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			moveMain(player);
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "Correct usage:" + ChatColor.GREEN + "/nether leave");
		}
		return false;
	}

	private void movePlayer(Player p) {
		if(InventoryRemoval.checkInventory(p)){
			NetherSQL.addPlayer(p);
			WorldHelper.addPlayer(p);
			p.sendMessage(ChatColor.BLUE + "Welcome to the nether!");
		}
	}
	
	private void moveAdmin(Player p) {
		WorldHelper.addPlayer(p);
		p.sendMessage(ChatColor.GREEN + "Welcome to the nether, Admin!");
	}
	
	private void moveMain(Player p) {
		WorldHelper.removePlayer(p);
		NetherSQL.removePlayer(p);
		player.sendMessage(ChatColor.BLUE + "Welcome back to the main world! You will be able to revisit the nether in 24 hours!");
	}

	public boolean playerHasWaited(Player p) {
		currentTime = System.currentTimeMillis();
		lastJoin = NetherSQL.getLastJoin(p);
		if(currentTime - lastJoin > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
			return true;
		} else {
			return false;
		}

	}

}
