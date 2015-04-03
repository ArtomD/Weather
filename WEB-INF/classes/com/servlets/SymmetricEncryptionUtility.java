package com.servlets;

import org.apache.commons.codec.binary.Hex;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;

import java.sql.SQLException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64; //20

public class SymmetricEncryptionUtility {

	public static String runEncrypt(String message, String password) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, java.security.spec.InvalidKeySpecException, BadPaddingException, NoSuchPaddingException {
		//turn the password into a 24 byte key and run the encrypt function on the message
		//returns the encrypted message

		//turn the password into a byte array
		byte[] oldbytePassword = password.getBytes("UTF-16");
		//initialize blank 24 byte array
		byte[] bytePassword = new byte[24];
		int length = oldbytePassword.length;
		if(length>24){
			length=24;
		}
		//overwrite the blank 24 byte array with values from the first byte array and trim off the excess values
		System.arraycopy(oldbytePassword, 0, bytePassword, 0, length);
		//create a DESedeKeySpec using the 24 byte array
		DESedeKeySpec keySpec = new DESedeKeySpec(bytePassword);
		//get an instance of the SecretKeyFactory in DESede
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
		//generate a key using the DESedeKeySpec
		SecretKey key = factory.generateSecret(keySpec);
	
		//run the encrypt function with the message and generated key and return its value
		return SymmetricEncryptionUtility.encrypt(message, key);
	}                            

	public static String runDecrypt(String message, String password) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IOException, InvalidAlgorithmParameterException, java.security.spec.InvalidKeySpecException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {	
		//turn the password into a 24 byte key and run the decrypt function on the message
		//returns the decrypted message

		//turn the password into a byte array
		byte[] oldbytePassword = password.getBytes("UTF-16");
		//initialize blank 24 byte array
		byte[] bytePassword = new byte[24];
		int length = oldbytePassword.length;
		if(length>24){
			length=24;
		}
		//overwrite the blank 24 byte array with values from the first byte array and trim off the excess values
		System.arraycopy(oldbytePassword, 0, bytePassword, 0, length);
		//create a DESedeKeySpec using the 24 byte array		
		DESedeKeySpec keySpec = new DESedeKeySpec(bytePassword);
		//get an instance of the SecretKeyFactory in DESede
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
		//generate a key using the DESedeKeySpec
		SecretKey key = factory.generateSecret(keySpec);
	
		//run the decrypt function with the message and generated key and return its value
		return SymmetricEncryptionUtility.decrypt(message, key);
	}

	public static final String encrypt(final String message, final Key key) throws IllegalBlockSizeException,
BadPaddingException, NoSuchAlgorithmException,
NoSuchPaddingException, InvalidKeyException,
UnsupportedEncodingException, InvalidAlgorithmParameterException {
		
		//create a new DESede Cipher object using a key
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE,key);
		//turn the message to be encrypted into a byte array
		byte[] stringBytes = message.getBytes();
		//run the byte array message through the Cipher object and saves the output to a new byte array
		byte[] raw = cipher.doFinal(stringBytes);
		//turns the encrypted byte array into a string and returns it
		return Base64.encodeBase64String(raw);
	}

	public static final String decrypt(final String encrypted,final Key key) throws InvalidKeyException,
NoSuchAlgorithmException, NoSuchPaddingException,
IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {

		//create a new DESede Cipher object using a key
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, key);
		//turn the message to be decrypted into a byte array
		byte[] raw = Base64.decodeBase64(encrypted);
		//run the byte array message through the Cipher object and saves the output to a new byte array
		byte[] stringBytes = cipher.doFinal(raw);
		//turns the encrypted byte array into a string and returns it
		String clearText = new String(stringBytes, "UTF8");
		return clearText;
	}
}

