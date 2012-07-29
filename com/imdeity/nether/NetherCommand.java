package com.imdeity.nether;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
	private Util util;

	public NetherCommand(DeityNether plugin){
		this.plugin = plugin;
		wh = new WorldHelper(plugin);
		util = new Util();

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commadLabel, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}

		if(args.length == 1 && args[0].equalsIgnoreCase("join")){
			try{ wh.addPlayer(player); }catch(Exception e){ e.printStackTrace(); }
		} else if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			wh.removePlayer(player);
		} else if(args.length == 1 && args[0].equalsIgnoreCase("?")) {
			Util.helpPlayer(player);
		} else if(args.length == 1 && args[0].equalsIgnoreCase("regen")){
			if(sender instanceof Player && player.isOp()) {
				plugin.getServer().broadcastMessage(ChatColor.RED + "[DeityNether] The nether is regenerating...");
				List<Entity> list =  plugin.getServer().getWorld("world_nether").getEntities();
				for(int i = 0; i < list.size(); i++){
					Entity e = list.get(i);
					if(e instanceof Player){
						e.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
						if(!player.hasPermission("Deity.nether.override")){
							NetherSQL.removePlayer((Player) e);
						}
					}else{

					}
				}
				plugin.config.set("last-reset", -1);
				try{ plugin.config.save(plugin.configFile); }catch (Exception e){ e.printStackTrace(); }
				plugin.getServer().shutdown();
			} else if(player == null) {
				List<Entity> list =  plugin.getServer().getWorld("world_nether").getEntities();
				plugin.getServer().broadcastMessage(ChatColor.RED + "[DeityNether] The nether is regenerating...");
				for(int i = 0; i < list.size(); i++){
					Entity e = list.get(i);
					if(e instanceof Player){
						e.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
						if(!((Player) e).hasPermission("Deity.nether.override")){
							NetherSQL.removePlayer((Player) e);
						}
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
		}else{
			player.sendMessage(ChatColor.RED + "[DeityNether] Try: " + ChatColor.GREEN + "/nether ?");

		}
		return true;	
	}
}