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
	
	public Long getIdBySessionToken(String sessionToken, String role) {
		return jedisSessionTokenDAO.getIdBySessionToken(sessionToken, role);
	}
}
