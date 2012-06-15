package com.imdeity.DeityKingdomsDev.DeityNether;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.SQL.NetherSQL;

public class NetherCommand implements CommandExecutor {
	private final DeityNether plugin;
	Player player;
	long lastJoin;
	
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
			}
		}
		
		return false;
	}

	private void movePlayer(Player p) {
		NetherSQL.addPlayer(p);
	}
	
	public boolean playerHasWaited(Player p2) {
		
		return false;
		
	}

}
