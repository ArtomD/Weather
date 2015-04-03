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



public class decryptServlet extends HttpServlet {

	private String target = "/Results2.jsp";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If it is a get request forward to doPost()
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the user text from the request
		String stringID = request.getParameter("ID");
		// Get the password from the request
		String password = request.getParameter("password2");
		//padds password to minimum length
		while(password.length()<5){
			password = password + "z";
		}
		//convert ID to int
		int ID = Integer.parseInt(stringID);
		//get the message from database using the ID
		String userText = database.extract(ID); 
		//initialize message
		String message = "filler";
		//decrypt the message using the password 	
		try{
			message = SymmetricEncryptionUtility.runDecrypt(userText, password);
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(java.security.spec.InvalidKeySpecException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(InvalidAlgorithmParameterException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		}    
		// records the text and id to pass it to the target
		request.setAttribute("userText", message);
		// Forward the request to the target named
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
 	}
	public void destroy() {
	}	
}
