package com.imdeity.nether.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.bukkit.entity.Player;

import com.imdeity.nether.*;

public class NetherSQL {

	private static Connection conn;
	static PreparedStatement state;
	static String sql;
	static String name;
	static long currentTime;
	static ResultSet result;
	static long resultInt;
	static long last;
	public static java.sql.Date sqlDate;
	public static Timestamp ts;
	public static int spent;

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
		sendSQLCommand("CREATE TABLE IF NOT EXISTS `deity_nether_action_log` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`action_type` CHAR( 1 ) NOT NULL ,"+
				"`time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"+
				") ENGINE = MYISAM COMMENT =  'Nether action log for records of player joins/leaves';");      
		sendSQLCommand("CREATE TABLE IF NOT EXISTS `deity_nether_stats` ("+
				"`id` INT( 16 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,"+
				"`player_name` VARCHAR( 32 ) NOT NULL ,"+
				"`time_in_nether` INT( 4 ) NOT NULL DEFAULT  '0' COMMENT  'time in seconds',"+
				"UNIQUE (`player_name`)"+
				") ENGINE = MYISAM COMMENT =  'Current time in nether record for players';");
	}

	public static boolean sendSQLCommand(String sql) {
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost/cliff", "mbon", "mbon");
			}
			state = conn.prepareStatement(sql);
			state.execute();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	public static ResultSet getResult(String sql) {
		try {
			if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://localhost/cliff", "mbon", "mbon");
			}
			state = conn.prepareStatement(sql);
			ResultSet result = state.executeQuery();
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static void addPlayer(Player p) {
		name = p.getName();
		currentTime = System.currentTimeMillis();
		sendSQLCommand("INSERT INTO `deity_nether_action_log` (`player_name`, `action_type`, `time`) VALUES ('" + name + "', 'J', NOW())");
		PlayerChecker.playersInNether.add(p);
		PlayerChecker.map.put(p, currentTime);
	}

	public static void removePlayer(Player p) {
		sendSQLCommand("INSERT INTO `deity_nether_action_log` (`player_name`, `action_type`, `time`) VALUES ('" + p.getName() + "', 'L', NOW())");

		ResultSet result = getResult("SELECT * FROM `deity_nether_stats` WHERE `player_name`='" + p.getName() + "'");
		try{
			if(!result.next()){
				sendSQLCommand("INSERT INTO `deity_nether_stats` (`player_name`, `time_in_nether`) VALUES ('" + p.getName() + "', " + ((System.currentTimeMillis() - PlayerChecker.map.get(p))/1000) + ")");
			}else{
				sendSQLCommand("UPDATE `deity_nether_stats` SET `time_in_nether`=" + ((System.currentTimeMillis() - PlayerChecker.map.get(p))/1000) + " WHERE `player_name`='" + p.getName() + "'");
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		PlayerChecker.playersInNether.remove(p);
		PlayerChecker.map.remove(p);
	}

	public static Date getLastJoin(Player p) throws SQLException {
		ResultSet result = getResult("select max(`time`) from `deity_nether_action_log` WHERE `player_name`='" + p.getName() + "' AND `action_type`='J'");
		try{
			result.next();
			ts = result.getTimestamp(1);
		}catch (Exception e){
			e.printStackTrace();
		}
		if(ts != null){
			Date date = new Date(ts.getTime());
			return date;
		}else{
			return null;
		}
	}
	
	public static int getTimeSpent(Player p) throws SQLException {
		ResultSet result = getResult("SELECT `time_in_nether` FROM `deity_nether_stats` WHERE `player_name`='" + p.getName() + "'");
		try{
			if(result == null) {
				return 0;
			}
			result.next();
			spent = result.getInt(4);
		} catch(Exception e) {
			e.printStackTrace();
		} if(result == null) {
			return 0;
		} else {
			return spent;
		}
	}
}

