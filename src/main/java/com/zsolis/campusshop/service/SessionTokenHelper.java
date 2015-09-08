package com.zsolis.campusshop.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.util.CryptUtil;

@Component
public class SessionTokenHelper {
	@Autowired
	private JedisSessionTokenDAO jedisSessionTokenDAO;
	@Autowired
	private CryptUtil cryptUtil;
	
	public SessionTokenHelper() {}
	
	public String setSessionToken(Long id, String role) {
		Long timestamp = new Date().getTime();
		String sessionToken = cryptUtil.encryptSHA1(id.toString() + timestamp.toString());
		jedisSessionTokenDAO.setSessionToken(sessionToken, id, role);
		return sessionToken;
	}
	
	public boolean checkSessionTokenExist(String sessionToken) {
		String mixed = jedisSessionTokenDAO.getMixedBySessionToken(sessionToken);
		if (mixed == null) {
			return false;
		}
		return true;
	}
	
	public boolean checkSessionTokenRole(String role, String sessionToken) {
		String mixed = jedisSessionTokenDAO.getMixedBySessionToken(sessionToken);
		if (mixed == null) {
			return false;
		}
		String [] splits = mixed.split("_");
		if (splits[0].equals(role)) {
			return true;
		}
		return false;
	}
	
	public boolean checkSessionTokenId(String role, Long id, String sessionToken) {
		String mixed = jedisSessionTokenDAO.getMixedBySessionToken(sessionToken);
		if (mixed == null) {
			return false;
		}
		String [] splits = mixed.split("_");
		if (splits[0].equals(role) && splits[1].equals(id.toString())) {
			return true;
		}
		return false;
	}
}
