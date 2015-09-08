package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class StoreController {
	@Autowired
	private StoreService storeService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/storesbycampus/{campusId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getStoresByCampus(@PathVariable("campusId") Long campusId, 
			@PathVariable("page") int page) {
		return storeService.getStoresByCampus(campusId, page);
	}
	
	@RequestMapping(value = "/userfavoritestores/{userId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getUserFavoriteStores(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return storeService.getUserFavoriteStores(userId, page);
	}
	
	@RequestMapping(value = "/store/{storeId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getStore(@PathVariable("storeId") Long storeId) {
		return storeService.getStore(storeId);
	}
	
	@RequestMapping(value = "/adduserfavoritestore/{userId}/{storeId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addUserFavoriteStore(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("storeId") Long storeId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = storeService.addUserFavoriteStore(userId, storeId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeuserfavoritestore/{userId}/{storeId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeUserFavoriteStore(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("storeId") Long storeId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = storeService.removeUserFavoriteStore(userId, storeId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/addstore", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addStore(@RequestBody Map<String, String> jsonMap) {
		if (storeService.addStore(jsonMap.get("account"), jsonMap.get("password"), jsonMap.get("name"), jsonMap.get("address"), jsonMap.get("phoneNumber")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/storechecklogin/{account}/{passwordAfterSalt}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> checkLogin(@PathVariable("account") String account, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt) {
		Map<String, String> result = storeService.checkLogin(account, passwordAfterSalt);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("∂¡»°¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/storesessiontoken/{authToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> getSessionToken(@PathVariable("authToken") String authToken) {
		String sessionToken = storeService.getSessionToken(authToken);
		if (sessionToken == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("sessionToken", sessionToken);
		return result;
	}
	
	@RequestMapping(value = "/setstoreonline/{storeId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setStoreOnline(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Long orderCount = storeService.setStoreOnline(storeId);
		Map<String, String> result = new HashMap<String, String>();
		result.put("orderCount", orderCount.toString());
		return result;
	}
	
	@RequestMapping(value = "/checkstoreonline/{storeId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> checkStoreOnline(@PathVariable("storeId") Long storeId) {
		Map<String, String> result = new HashMap<String, String>();
		if (storeService.checkStoreOnline(storeId)) {
			result.put("online", "true");
		} else {
			result.put("online", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/storesetpassword/{storeId}/{passwordAfterSalt}/{password}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setPassword(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("storeId") Long storeId, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt, 
			@PathVariable("password") String password) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (storeService.checkPassword(storeId, passwordAfterSalt)) {
			Map<String, String> result = storeService.setPassword(storeId, password);
			if (result == null) {
				return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
			}
			return result;
		}
		return ResponseStatusHelper.getErrorResponse("password¥ÌŒÛ");
	}
	
	@RequestMapping(value = "/setstore/{storeId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setStore(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("storeId") Long storeId,
			@RequestBody Map<String, Object> jsonMap) {
		Map<String, String> result = storeService.setStore(storeId, (String)jsonMap.get("account"), (String)jsonMap.get("password"), (String)jsonMap.get("name"), 
				(String)jsonMap.get("address"), (String)jsonMap.get("phoneNumber"), (Float)jsonMap.get("minimumAmount"), (Float)jsonMap.get("deliveryFee"), (String)jsonMap.get("imagePath"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
