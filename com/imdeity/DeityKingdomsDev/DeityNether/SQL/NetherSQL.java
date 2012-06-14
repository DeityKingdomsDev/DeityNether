package com.imdeity.DeityKingdomsDev.DeityNether.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class NetherSQL {

	private static Connection conn;
	static PreparedStatement state;
	static String sql;
	
	public NetherSQL() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		checkTables();
	}
	
	public static void checkTables(){
		//id, player name, enter-time (13), leave time (13), time in nether (millis), time in nether (minutes)
		sendSQLCommand("CREATE TABLE IF NOT EXISTS nether (`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `player_name` VARCHAR(16) NOT NULL, `enter_time` INT(20) NOT NULL, `leave_time` INT(20) NOT NULL, `duration` INT(20) NOT NULL, `duration_mins` INT(10) NOT NULL)");                       
	}
	
	public static boolean sendSQLCommand(String sql) {
		try {
			if(conn.isValid(5)){
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		try {
			state = conn.prepareStatement(sql);
			state.executeUpdate();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		
	}
}
