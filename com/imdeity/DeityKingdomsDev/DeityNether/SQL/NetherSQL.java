package com.imdeity.DeityKingdomsDev.DeityNether.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

import com.imdeity.DeityKingdomsDev.DeityNether.PlayerChecker;

public class NetherSQL {

	private static Connection conn;
	static PreparedStatement state;
	static String sql;
	static String name;
	static int currentTime;
	static ResultSet result;
	static int last;

	public NetherSQL() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkTables();

	}

	public static void checkTables(){
		sendSQLCommand("CREATE TABLE IF NOT EXISTS nether (`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `player_name` VARCHAR(16) NOT NULL, `enter_time` INT(20) NOT NULL, `leave_time` INT(20) NOT NULL, `duration` INT(20) NOT NULL, `duration_mins` INT(10) NOT NULL, PRIMARY KEY(`id`))");                       
	}

	public static boolean sendSQLCommand(String sql) {
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/cliff", "root", "root");
			}
			state = conn.prepareStatement(sql);
			state.executeUpdate();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	private static int getInt(String sql){
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/cliff", "root", "root");
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			return result.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}

	public static int getLastJoin(Player p){
		name = p.getName();
		last = getInt("select max(enter_time) from nether WHERE `player_name`=`" + name + "`");
		if(last == -1){
			return -1;
		} else {
			return last;
		}

	}
	public static void addPlayer(Player p) {
		name = p.getName();
		currentTime = (int) System.currentTimeMillis();
		sendSQLCommand("INSERT INTO nether (`player_name`, `enter_time`, `leave_time`, `duration`, `duration_mins`) VALUES (`" + name + "`, `" + currentTime + "`, `0`, `0`, `0`)");
		PlayerChecker.playersInNether.add(p);
		PlayerChecker.map.put(p, currentTime);
	}

	public static void removePlayer(Player p) {
		sendSQLCommand("UPDATE `nether` SET `leave_time`=`" + (int)System.currentTimeMillis() + "' WHERE ``player_name`=`" + p.getName() + "` AND WHERE `enter_time`=`" + PlayerChecker.map.get(p) +"` )");
		sendSQLCommand("UPDATE `nether` SET `duration`=`" + ((int)System.currentTimeMillis() - PlayerChecker.map.get(p)) + "' WHERE ``player_name`=`" + p.getName() + "` AND WHERE `enter_time`=`" + PlayerChecker.map.get(p) +"` )");
		sendSQLCommand("UPDATE `nether` SET `duration_mins`=`" + ((((int)System.currentTimeMillis() - PlayerChecker.map.get(p))/1000)/60) + "' WHERE ``player_name`=`" + p.getName() + "` AND WHERE `enter_time`=`" + PlayerChecker.map.get(p) +"` )");
		PlayerChecker.playersInNether.remove(p);
		PlayerChecker.map.remove(p);

	}
}
