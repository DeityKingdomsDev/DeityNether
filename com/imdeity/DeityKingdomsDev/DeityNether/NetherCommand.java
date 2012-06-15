package com.imdeity.DeityKingdomsDev.DeityNether;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.SQL.NetherSQL;
import com.imdeity.DeityKingdomsDev.DeityNether.helpers.WorldHelper;

public class NetherCommand implements CommandExecutor {
	private final DeityNether plugin;
	Player player;
	int lastJoin;
	int currentTime;
	
	public NetherCommand(DeityNether plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		player = (Player) sender;
		if(player.hasPermission("Deity.nether.override")){
			movePlayer(player);
		}else if(player.hasPermission("Deity.nether.general")){
			if(playerHasWaited(player)){
				movePlayer(player);
			} else {
				return false;
			}
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
		}
		
		return false;
	}

	private void movePlayer(Player p) {
		NetherSQL.addPlayer(p);
		WorldHelper.removePlayer(p);
	}
	
	public boolean playerHasWaited(Player p) {
		currentTime = (int) System.currentTimeMillis();
		lastJoin = NetherSQL.getLastJoin(p);
		if(currentTime - lastJoin > DeityNether.PLAYER_JOIN_NETHER_WAIT_MILLIS){
			return true;
		} else {
			return false;
		}
		
	}

}
