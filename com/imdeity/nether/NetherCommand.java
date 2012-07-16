package com.imdeity.nether;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.imdeity.nether.sql.NetherSQL;
import com.imdeity.nether.helpers.*;
import com.imdeity.nether.DeityNether;

public class NetherCommand implements CommandExecutor {
	private final DeityNether plugin;
	Player player;
	long lastJoin;
	long currentTime;
	private WorldHelper wh;
	ArrayList<Player> triedToLeave = new ArrayList<Player>();

	public NetherCommand(DeityNether plugin){
		this.plugin = plugin;
		wh = new WorldHelper(plugin);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commadLabel, String[] args) {
		player = (Player) sender;
		if(args.length == 1 && args[0].equalsIgnoreCase("join")){
			if(player.hasPermission("Deity.nether.override") || player.isOp()){
				if(InventoryRemoval.checkInventory(player)){
					moveOverrider(player);
				}else{
					player.sendMessage(ChatColor.RED + "[DeityNether] You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
				}
			} else if(player.hasPermission("Deity.nether.general")){
				try {
					if(playerHasWaited(player)) {
						movePlayer(player);
					} else {
						player.sendMessage(ChatColor.RED + "[DeityNether] You must wait " + ChatColor.GREEN + DeityNether.PLAYER_JOIN_NETHER_WAIT_HOURS + ChatColor.RED + " hours before entering the nether again!");
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				player.sendMessage(ChatColor.RED + "[DeityNether] You do not have permission to use that command!");
				return true;
			}
		} else if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			if(player.hasPermission("Deity.nether.override") || player.isOp()){
				moveMain(player);
			}else{
				if(!triedToLeave.contains(player)){
					player.sendMessage(ChatColor.RED + "[DeityNether]" + ChatColor.GREEN + " Are you sure you want to leave? You still have " + ChatColor.BLUE + (DeityNether.NETHER_TIME_LIMIT_MINUTES - (((System.currentTimeMillis() - PlayerChecker.map.get(player))/1000)/60)) + ChatColor.GREEN + " minutes left in the nether! Do " + ChatColor.BLUE + "/nether leave" + ChatColor.GREEN + " again if you really want to leave.");
					triedToLeave.add(player);
				}else{
					moveMain(player);
					triedToLeave.remove(player);
				}
			}
			return true;
		} else if(args.length == 1 && args[0].equalsIgnoreCase("?")) {
			if(player.hasPermission("Deity.nether.general") || player.hasPermission("Deity.nether.override")) {
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "Commands:");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "+......................+");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether join" + ChatColor.AQUA+ " Teleports you to the nether.");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether leave" + ChatColor.AQUA + " Teleports you back to the main world.");
				return true;
			} else if(player.isOp()){
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "Commands:");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "+......................+");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether join" + ChatColor.AQUA+ " Teleports you to the nether.");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether leave" + ChatColor.AQUA + " Teleports you back to the main world.");
				player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether regen" + ChatColor.AQUA + " Regenerates the nether world.");
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
			}
		} else if(args.length == 1 && args[0].equalsIgnoreCase("regen") && player.isOp()){
			List<Entity> list =  plugin.getServer().getWorld("world_nether").getEntities();
			plugin.getServer().broadcastMessage(ChatColor.RED + "[DeityNether] The nether is regenerating...");
			for(int i = 0; i < list.size(); i++){
				Entity e = list.get(i);
				if(e instanceof Player){
					e.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
				}else{

				}
			}
			plugin.config.set("last-reset", -1);
			try{ plugin.config.save(plugin.configFile); }catch (Exception e){ e.printStackTrace(); }
			plugin.getServer().shutdown();
		}else{
			player.sendMessage(ChatColor.RED + "[DeityNether] Try: " + ChatColor.GREEN + "/nether ?");

		}
		return true;
	}

	private void movePlayer(Player p) {
		if(InventoryRemoval.checkInventory(p)){
			NetherSQL.addPlayer(p);
			wh.addPlayer(p);
			p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome to the nether!");
		}else{
			p.sendMessage(ChatColor.RED + "[DeityNether] You may only bring tools, swords, armor, and food into the nether. Two " + ChatColor.GOLD + "gold blocks " + ChatColor.RED + "will be taken as an entry fee. Please take all other blocks/items out of your inventory.");
		}
	}

	private void moveOverrider(Player p) {
		wh.addPlayer(p);
		p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "Welcome to the nether. You will have no time limit. Enjoy your stay!");
	}

	private void moveMain(Player p) {
		wh.removePlayer(p);
		if(p.hasPermission("Deity.nether.override")){

		}else{
			NetherSQL.removePlayer(p);
			player.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.BLUE + "Welcome back to the main world! You will be able to revisit the nether in 24 hours!");
		}
	}

	public boolean playerHasWaited(Player p) throws SQLException {
		Date lastJoin = NetherSQL.getLastJoin(p);
		int timeSpent = NetherSQL.getTimeSpent(p);
		int timeAllowed = DeityNether.NETHER_TIME_LIMIT_MINUTES;
		if(lastJoin == null) {
			return true;
		} else if(lastJoin != null && timeSpent < timeAllowed) {
			return true;
		} else {
			return false;
		}
	}
}