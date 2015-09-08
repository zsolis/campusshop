package com.zsolis.campusshop.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.CryptUtil;

@Service
public class StoreService {
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private CryptUtil cryptUtil;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	@Autowired
	private JedisStoreOrderDAO jedisStoreOrderDAO;
	@Autowired
	private JedisStoreOnlineDAO jedisStoreOnlineDAO;
	
	public StoreService() {}
	
	public List<Map<String, Object>> getStoresByCampus(Long campusId, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return storeDAO.getStoresByCampusOrderedByPriority(campus, CampusStatus.normal, page);
	}
	
	public List<Map<String, Object>> getUserFavoriteStores(Long userId, int page) {
		User user = new TemporaryUser();
		user.setId(userId);
		return storeDAO.getUserFavoriteStoresOrderedByDate(user, page);
	}
	
	public Map<String, Object> getStore(Long storeId) {
		Store store = storeDAO.getStoreById(storeId);
		if(store == null) {
			return null;
		}
		return storeDAO.getStoreMapByStore(store);
	}
	
	public Map<String, String> addUserFavoriteStore(Long userId, Long storeId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Store store = new Store();
		store.setId(storeId);
		storeDAO.addUserFavoriteStore(user, store);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> removeUserFavoriteStore(Long userId, Long storeId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Store store = new Store();
		store.setId(storeId);
		storeDAO.removeUserFavoriteStore(user, store);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Long addStore(String account, String password, String name, String address, String phoneNumber) {
		if(storeDAO.getStoreByAccount(account) != null) {
			return null;
		}
		return storeDAO.addStore(account, password, name, address, phoneNumber);
	}
	
	public boolean checkPassword(Long storeId, String passwordAfterSalt) {
		Store store = storeDAO.getStoreById(storeId);
		if(store == null) {
			return false;
		}
		return cryptUtil.comparePassword(passwordAfterSalt, store.getPassword());
	}
	
	public Map<String, String> checkLogin(String account, String passwordAfterSalt) {
		Store store = storeDAO.getStoreByAccount(account);
		Map<String, String> result = new HashMap<String, String>();
		if(store == null) {
			result.put("status", "error");
			result.put("reason", "账号错误");
			return result;
		}
		if(!cryptUtil.comparePassword(passwordAfterSalt, store.getPassword())) {
			result.put("status", "error");
			result.put("reason", "密码错误");
			return result;
		}
		result.put("status", "ok");
		result.put("authToken", store.getAuthToken());
		result.put("sessionToken", getSessionToken(store.getAuthToken()));
		return result;
	}
	
	public String getSessionToken(String authToken) {
		Store store = storeDAO.getStoreByAuthToken(authToken);
		if(store == null) {
			return null;
		}
		return sessionTokenHelper.setSessionToken(store.getId(), "Store");
	}
	
	public Long setStoreOnline(Long storeId) {
		jedisStoreOnlineDAO.setStoreOnline(storeId);
		return jedisStoreOrderDAO.getStoreOrderCount(storeId);
	}
	
	public boolean checkStoreOnline(Long storeId) {
		return jedisStoreOnlineDAO.checkStoreOnline(storeId);
	}
	
	public Map<String, String> setPassword(Long storeId, String password) {
		Store store = storeDAO.getStoreById(storeId);
		if (store == null) {
			return ResponseStatusHelper.getErrorResponse("storeId错误");
		}
		store.setPassword(password);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> setStore(Long storeId, String name, String phoneNumber, String address, String description, String statement, Float minimumAmount, Float deliveryFee, String imagePath) {
		Store store = storeDAO.getStoreById(storeId);
		if(store == null) {
			return ResponseStatusHelper.getErrorResponse("storeId错误");
		}
		if(name != null) {
			store.setName(name);
		}
		if (phoneNumber != null) {
			store.setPhoneNumber(phoneNumber);
		}
		if(address != null) {
			store.setAddress(address);
		}
		if (description != null) {
			store.setDescription(description);
		}
		if (statement != null) {
			store.setStatement(statement);
		}
		if (minimumAmount != null) {
			store.setMinimumAmount(minimumAmount);
		}
		if(deliveryFee != null) {
			store.setDeliveryFee(deliveryFee);
		}
		if(imagePath != null) {
			store.setImagePath(imagePath);
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
