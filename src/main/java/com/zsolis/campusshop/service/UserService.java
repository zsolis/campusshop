package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.*;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private VerifyHelper verifyHelper;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	@Autowired
	private CryptUtil cryptUtil;
	
	public UserService() {}
	
	public Map<String, Object> getUser(Long userId) {
		User user = userDAO.getUserById(userId);
		if(user == null) {
			return null;
		}
		return userDAO.getUserMapByUser(user);
	}
	
	public Map<String, String> sendVerifyCode(String phoneNumber) {
		if(userDAO.getUserByPhoneNumber(phoneNumber) != null) {
			return ResponseStatusHelper.getErrorResponse("phoneNumber错误");
		}
		int resultCode = verifyHelper.sendVerifyCode(phoneNumber);
		if (resultCode == 1) {
			return ResponseStatusHelper.getOkResponse();
		} else {
			return ResponseStatusHelper.getErrorResponse("错误代码" + resultCode);
		}
	}
	
	public Long addRegisteredUser(String phoneNumber, String password, Long campusId) {
		if(userDAO.getUserByPhoneNumber(phoneNumber) != null) {
			return null;
		}
		Campus campus = new Campus();
		campus.setId(campusId);
		return userDAO.addRisteredUser(phoneNumber, password, campus);
	}
	
	public Long addTemporaryUser(Long campusId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return userDAO.addTemporaryUser(campus);
	}
	
	public Map<String, String> switchRegisteredUser(Long userId, String phoneNumber, String password) {
		userDAO.switchRegisteredUser(userId, phoneNumber, password);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> checkLogin(String phoneNumber, String passwordAfterSalt) {
		Map<String, String> result = new HashMap<String, String>();
		RegisteredUser user = userDAO.getUserByPhoneNumber(phoneNumber);
		if(user == null) {
			result.put("status", "error");
			result.put("reason", "手机号错误");
			return result;
		}
		if(!cryptUtil.comparePassword(passwordAfterSalt, user.getPassword())) {
			result.put("status", "error");
			result.put("reason", "密码错误");
			return result;
		}
		result.put("status", "ok");
		result.put("authToken", user.getAuthToken());
		result.put("sessionToken", getSessionToken(user.getAuthToken()));
		return result;
	}
	
	public String getSessionToken(String authToken) {
		User user = userDAO.getUserByAuthToken(authToken);
		if(user == null) {
			return null;
		}
		String sessionToken = sessionTokenHelper.setSessionToken(user.getId(), "User");
		return sessionToken;
	}
	
	public boolean checkPassword(Long userId, String passwordAfterSalt) {
		RegisteredUser user = (RegisteredUser)userDAO.getUserById(userId);
		if(user == null) {
			return false;
		}
		return cryptUtil.comparePassword(passwordAfterSalt, user.getPassword());
	}
	
	public Map<String, String> setPassword(Long userId, String password) {
		RegisteredUser user = (RegisteredUser)userDAO.getUserById(userId);
		if (user == null) {
			return ResponseStatusHelper.getErrorResponse("userId");
		}
		user.setPassword(password);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> setPhoneNumber(Long userId, String phoneNumber) {
		RegisteredUser user = (RegisteredUser)userDAO.getUserById(userId);
		if (user == null) {
			return ResponseStatusHelper.getErrorResponse("userId");
		}
		user.setPhoneNumber(phoneNumber);
		return ResponseStatusHelper.getOkResponse();
	}
}
