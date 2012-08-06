package com.imdeity.nether.sql;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;
import com.imdeity.nether.helpers.PlayerChecker;

public class NetherSQL {
	
	public static void addPlayer(Player p) {
		String sql = "INSERT INTO `deity_nether_action_log` (`player_name`, `action_type`, `time`) VALUES (?, 'J', NOW())";
		DeityAPI.getAPI().getDataAPI().getMySQL().write(sql, p);
	}
	
	public static void removePlayer(Player p) {
		String sql = "INSERT INTO `deity_nether-action_log` (`player_name`, `action_type`, `time`) VALUES (?, 'L', NOW())";
		DeityAPI.getAPI().getDataAPI().getMySQL().write(sql, p);
		
		String sql2 = "SELECT * FROM `deity_nether_stats` WHERE `player_name`=?";
		DatabaseResults query = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced(sql2, p);
		try {
			if(query == null) {
				long timeInNether = (System.currentTimeMillis()-PlayerChecker.playerMap.get(p))/1000;
				String sql3 = "INSERT INTO `deity_nether_stats` (`player_name`, `time_in_nether`) VALUES (?, ?)";
				DeityAPI.getAPI().getDataAPI().getMySQL().write(sql3, p, timeInNether);
			} else {
				long timeInNether = (System.currentTimeMillis()-PlayerChecker.playerMap.get(p))/1000;
				String sql4 = "UPDATE `deity_nether_stats` SET `time_in_nether`=? WHERE `player_name`=?";
				DeityAPI.getAPI().getDataAPI().getMySQL().write(sql4, timeInNether, p);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getTimeSpent(Player player) {
		String sql = "SELECT * FROM `deity_nether_stats` WHERE `player_name`=?";
		DatabaseResults query = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced(sql, player.getName());
		if(query != null && query.hasRows()) {
			try {
				return (query.getInteger(0, "time_in_nether"));
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
