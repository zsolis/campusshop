package com.zsolis.campusshop.util;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class CryptUtil {
	private String salt;
	
	public CryptUtil() {
//		salt = encryptSHA1(new Date().toString());
		salt = "2157520A8F83F51C9F7BD2F9E5EE1C01E304DB3F";
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void changeSalt() {
		salt = encryptSHA1(new Date().toString());
	}
	
	public String encryptSHA1(String string) {
		String hexString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(string.getBytes("UTF-8"));
			hexString = bytes2HexString(digest.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString;
	}
	
	public boolean comparePassword(String afterSalt, String beforeSalt) {
		if(encryptSHA1(beforeSalt + salt).equals(afterSalt)) {
			return true;
		}
		return false;
	}
	
	private String bytes2HexString(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result += hex.toUpperCase();
		}
		return result;
	}
}
