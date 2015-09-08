package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.Administrator;
import com.zsolis.campusshop.domain.CampusAdministrator;
import com.zsolis.campusshop.util.*;

@Service
public class AdministratorService {
	@Autowired
	private AdministratorDAO administratorDAO;
	@Autowired
	private CryptUtil cryptUtil;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	public AdministratorService() {}
	
	public List<Map<String, Object>> getCampusAdministrators() {
		return administratorDAO.getCampusAdministrators();
	}
	
	public Map<String, Object> getCampusAdministrator(Long administratorId) {
		CampusAdministrator administrator = (CampusAdministrator)administratorDAO.getAdministratorById(administratorId);
		if (administrator == null) {
			return null;
		}
		return administratorDAO.getCampusAdministratorMapByCampusAdministrator(administrator);
	}
	
	public Long addCampusAdministrator(String account, String password, String name, String phoneNumber) {
		if(administratorDAO.getAdministratorByAccount(account) != null) {
			return null;
		}
		return administratorDAO.addCampusAdministrator(account, password, name, phoneNumber);
	}
	
	public boolean checkPassword(Long administratorId, String passwordAfterSalt) {
		Administrator administrator = administratorDAO.getAdministratorById(administratorId);
		if(administrator == null) {
			return false;
		}
		return cryptUtil.comparePassword(passwordAfterSalt, administrator.getPassword());
	}
	
	public Map<String, String> checkLogin(String account, String passwordAfterSalt) {
		Administrator administrator = administratorDAO.getAdministratorByAccount(account);
		Map<String, String> result = new HashMap<String, String>();
		if(administrator == null) {
			result.put("status", "error");
			result.put("reason", "’À∫≈¥ÌŒÛ");
			return result;
		}
		if(!cryptUtil.comparePassword(passwordAfterSalt, administrator.getPassword())) {
			result.put("status", "error");
			result.put("reason", "√‹¬Î¥ÌŒÛ");
			return result;
		}
		result.put("status", "ok");
		result.put("authToken", administrator.getAuthToken());
		result.put("sessionToken", getSessionToken(administrator.getAuthToken()));
		return result;
	}
	
	public String getSessionToken(String authToken) {
		Administrator administrator = administratorDAO.getAdministratorByAuthToken(authToken);
		if(administrator == null) {
			return null;
		}
		return sessionTokenHelper.setSessionToken(administrator.getId(), "Admin");
	}
	
	public Map<String, String> setPassword(Long administratorId, String password) {
		Administrator administrator = administratorDAO.getAdministratorById(administratorId);
		if(administrator == null) {
			return ResponseStatusHelper.getErrorResponse("administratorId¥ÌŒÛ");
		}
		administrator.setPassword(password);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> setCampusAdministrator(Long administratorId, String account, String name, String phoneNumber) {
		CampusAdministrator administrator = (CampusAdministrator)administratorDAO.getAdministratorById(administratorId);
		if(administrator == null) {
			return ResponseStatusHelper.getErrorResponse("administratorId¥ÌŒÛ");
		}
		if (account != null) {
			administrator.setAccount(account);
		}
		if(name != null) {
			administrator.setName(name);
		}
		if(phoneNumber != null) {
			administrator.setPhoneNumber(phoneNumber);
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
