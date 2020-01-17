package com.bank.util;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Create JDBC connection
 * */
public class DBConnection {
	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "cai";
		String password = "pass";
		return connection = DriverManager.getConnection(url,user,password);
	}
	
	
	

	
	
}
