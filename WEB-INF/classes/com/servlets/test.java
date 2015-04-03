//package com.servlets;

import com.servlets.*;
import javax.crypto.*;
import java.security.*;
import java.io.*;
import java.util.*;


public class test{  

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		String password = input.nextLine();
		System.out.println(password);
		try{
		  	byte[] oldbytePassword = password.getBytes("UTF-16");
			System.out.println(Arrays.toString(oldbytePassword));
			byte[] bytePassword = new byte[24];
			System.out.println(Arrays.toString(bytePassword));
			int length = oldbytePassword.length;
			if(length>24){
				length=24;
			}
			System.out.println(length);
			System.arraycopy(oldbytePassword, 0, bytePassword, 0, length);
			System.out.println(Arrays.toString(bytePassword));
	  	}catch(Exception e){
			e.printStackTrace();	  	
	  	}	
	}
}
