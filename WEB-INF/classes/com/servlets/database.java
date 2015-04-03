package com.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.util.*;

public class database {

	public static int insert(String userText){
		
		
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dateTime.format(dt);
		int id=0;
			try {
				Connection connection = null;
				ResultSet rs = null;
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/encrption", "root", "");
				Statement stmt = connection.createStatement();
				stmt.executeUpdate("INSERT INTO messages (datemessages, messages) VALUES ('"+time+"', '"+userText+"');");
				rs = stmt.executeQuery("SELECT idmessages FROM messages WHERE datemessages='"+time+"' AND messages='"+userText+"';");
				if(rs.next()){
	        			id = rs.getInt("idmessages");  
	        		}
				
				stmt.close();
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return id;
	}

	public static String extract(int ID){
		String message = "";
		try {
			
			Connection connection = null;
			ResultSet rs = null;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/encrption", "root", "");
			Statement stmt = connection.createStatement();				
			rs = stmt.executeQuery("SELECT messages FROM messages WHERE idmessages='"+ID+"';");
			if(rs.next()){
	        		message = rs.getString("messages");  
	        	}
				
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	return message;
	}

	

	
		
}
