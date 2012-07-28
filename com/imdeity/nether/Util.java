package com.imdeity.nether;

import java.sql.SQLException;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.imdeity.nether.sql.NetherSQL;

public class Util {

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

	public static void helpPlayer(Player p) {
		p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "Commands:");
		p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.AQUA + "+......................+");
		p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether join" + ChatColor.AQUA+ " Teleports you to the nether.");
		p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether leave" + ChatColor.AQUA + " Teleports you back to the main world.");
		if(p.isOp()){
			p.sendMessage(ChatColor.RED + "[DeityNether] " + ChatColor.GREEN + "/nether regen" + ChatColor.AQUA + " Regenerates the nether world.");
		}
	}
}