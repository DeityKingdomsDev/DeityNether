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
	static long currentTime;
	static ResultSet result;
	static long resultInt;
	static long last;

	public NetherSQL() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("[DeityNether] mySQL connection established!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkTables();

	}

	public static void checkTables(){
		sendSQLCommand("CREATE TABLE IF NOT EXISTS nether (`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `player_name` VARCHAR(16) NOT NULL, `enter_time` BIGINT(20) NOT NULL, `leave_time` BIGINT(20) NOT NULL, `duration` BIGINT(20) NOT NULL, `duration_mins` INT(10) NOT NULL, PRIMARY KEY(`id`))");                       
	}

	public static boolean sendSQLCommand(String sql) {
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/cliff", "root", "root");
			}
			state = conn.prepareStatement(sql);
			state.execute();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	private static long getLong(String sql){
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/cliff", "root", "root");
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next()){
				resultInt = result.getLong(1);
			}
			return resultInt;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}

	public static long getLastJoin(Player p){
		name = p.getName();
		last = getLong("select max(`enter_time`) from `nether` WHERE `player_name`='" + name + "'");
		if(last == -1){
			return -1;
		} else {
			return last;
		}

	}
	public static void addPlayer(Player p) {
		name = p.getName();
		currentTime = System.currentTimeMillis();
		sendSQLCommand("INSERT INTO `nether` (`player_name`, `enter_time`, `leave_time`, `duration`, `duration_mins`) VALUES ('" + name + "', " + currentTime + ", 0, 0, 0)");
		PlayerChecker.playersInNether.add(p);
		PlayerChecker.map.put(p, currentTime);
	}

	public static void removePlayer(Player p) {
		System.out.println("UPDATE `nether` SET `leave_time`=" + System.currentTimeMillis() + " WHERE `player_name`='" + p.getName() + "' AND `enter_time`=" + PlayerChecker.map.get(p));
		sendSQLCommand("UPDATE `nether` SET `leave_time`=" + System.currentTimeMillis() + " WHERE `player_name`='" + p.getName() + "' AND `enter_time`=" + PlayerChecker.map.get(p));
		sendSQLCommand("UPDATE `nether` SET `duration`=" + (System.currentTimeMillis() - PlayerChecker.map.get(p)) + " WHERE `player_name`='" + p.getName() + "' AND `enter_time`=" + PlayerChecker.map.get(p));
		sendSQLCommand("UPDATE `nether` SET `duration_mins`=" + (((System.currentTimeMillis() - PlayerChecker.map.get(p))/1000)/60) + " WHERE `player_name`='" + p.getName() + "' AND `enter_time`=" + PlayerChecker.map.get(p));
		PlayerChecker.playersInNether.remove(p);
		PlayerChecker.map.remove(p);

	}
}
