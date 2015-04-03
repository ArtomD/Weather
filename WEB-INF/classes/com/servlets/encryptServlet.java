package com.servlets;

import com.servlets.*;
import javax.crypto.*;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class encryptServlet extends HttpServlet {

	private String target = "/Results.jsp";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If it is a get request forward to doPost()
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ip = "127.0.0.1";
		//stores the encypted text in the database and get the correponding id
		int num = database.insert(ip);	
		
	} 
	public void destroy() {
	}	
}
