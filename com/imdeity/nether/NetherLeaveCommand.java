package com.imdeity.nether;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import com.imdeity.nether.helpers.WorldHelper;
import com.imdeity.nether.sql.NetherSQL;

public class NetherLeaveCommand implements CommandExecutor {
	private DeityNether plugin;
	Player player;
	public NetherLeaveCommand(DeityNether plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			moveMain(player);
			return true;
		}else{
			player.sendMessage(ChatColor.RED + "Correct usage:" + ChatColor.GREEN + "/nether leave");
		}
		return false;
	}
	private void moveMain(Player p) {
		WorldHelper.removePlayer(p);
		NetherSQL.removePlayer(p);
		player.sendMessage(ChatColor.BLUE + "Welcome back to the main world! You will be able to revisit the nether in 24 hours!");
	}
}
