package com.imdeity.nether.sql;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;

public class NetherSQL {
	
	public static void addPlayer(Player p) {
		String sql = "";
		DeityAPI.getAPI().getDataAPI().getMySQL().write(sql, p);
	}
	
	public static void removePlayer(Player p) {
		
	}
	
	public static int getTimeSpent(Player player) {
		String sql = "SELECT * FROM `deity_nether_stats` WHERE `player_name`=?";
		DatabaseResults query = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced(sql, player);
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
