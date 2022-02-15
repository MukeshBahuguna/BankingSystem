package com.revature.bankingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfigure {
	
	public static String url ="jdbc:postgresql://localhost:5432/postgres"; 
	public static String user="postgres";
	public static String password="060100eEe!";
	
	public static Connection DBConnection() {
		Connection con =null;
		try {
			Class.forName("org.postgresql.Driver");
			con =DriverManager.getConnection(url , user ,password);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
		
	}
}