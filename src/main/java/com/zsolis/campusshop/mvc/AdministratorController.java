package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/campusadministrators/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusAdministrators(@PathVariable("sessionToken") String sessionToken) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return null;
		}
		return administratorService.getCampusAdministrators();
	}
	
	@RequestMapping(value = "/campusadministrator/{administratorId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCampusAdministrator(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("administratorId") Long administratorId) {
		if (!sessionTokenHelper.checkSessionTokenId("Admin", administratorId, sessionToken)) {
			return null;
		}
		return administratorService.getCampusAdministrator(administratorId);
	}
	
	@RequestMapping(value = "/addcampusadministrator", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampusAdministrator(@RequestBody Map<String, String> jsonMap) {
		if (administratorService.addCampusAdministrator(jsonMap.get("account"), jsonMap.get("password"), jsonMap.get("name"), jsonMap.get("phoneNumber")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/administratorchecklogin/{account}/{passwordAfterSalt}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> checkLogin(@PathVariable("account") String account, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt) {
		Map<String, String> result = administratorService.checkLogin(account, passwordAfterSalt);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("∂¡»°¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/administratorsessiontoken/{authToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> getSessionToken(@PathVariable("authToken") String authToken) {
		String sessionToken = administratorService.getSessionToken(authToken);
		if (sessionToken == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("sessionToken", sessionToken);
		return result;
	}
	
	@RequestMapping(value = "/administratorsetpassword/{administratorId}/{passwordAfterSalt}/{password}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setPassword(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("administratorId") Long administratorId, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt, 
			@PathVariable("password") String password) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin",sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (administratorService.checkPassword(administratorId, passwordAfterSalt)) {
			Map<String, String> result = administratorService.setPassword(administratorId, password);
			if (result == null) {
				return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
			}
			return result;
		}
		return ResponseStatusHelper.getErrorResponse("password¥ÌŒÛ");
	}
	
	@RequestMapping(value = "/administratorsetpasswordbysuper/{administratorId}/{password}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setPassword(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("administratorId") Long administratorId, 
			@PathVariable("password") String password) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorService.setPassword(administratorId, password);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setcampusadministrator/{administratorId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCampusAdministrator(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("administratorId") Long administratorId, 
			@RequestBody Map<String, String> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorService.setCampusAdministrator(administratorId, jsonMap.get("account"), jsonMap.get("name"), jsonMap.get("phoneNumber"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
