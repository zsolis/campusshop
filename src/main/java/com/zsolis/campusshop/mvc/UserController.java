package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	@Autowired
	private VerifyHelper verifyHelper;
	
	@RequestMapping(value = "/user/{userId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getUser(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("userId") Long userId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return userService.getUser(userId);
	}
	
	@RequestMapping(value = "/sendverifycode/{phoneNumber}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> sendVerifyCode(@PathVariable("phoneNumber") String phoneNumber) {
		Map<String, String> result = userService.sendVerifyCode(phoneNumber);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/addregistereduser/{campusId}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addRegisteredUser(@PathVariable("campusId") Long campusId,
			@RequestBody Map<String, String> jsonMap) {
		if (!verifyHelper.verifyInput(jsonMap.get("phoneNumber"), jsonMap.get("verifyCode"))) {
			return ResponseStatusHelper.getErrorResponse("verifyCode错误");
		}
		if (userService.addRegisteredUser(jsonMap.get("phoneNumber"), jsonMap.get("password"), campusId) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/addtemporaryuser/{campusId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addTemporaryUser(@PathVariable("campusId") Long campusId) {
		if (userService.addTemporaryUser(campusId) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/switchregistereduser/{userId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> switchRegisteredUser(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("userId") Long userId,
			@RequestBody Map<String, String> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (!verifyHelper.verifyInput(jsonMap.get("phoneNumber"), jsonMap.get("verifyCode"))) {
			return ResponseStatusHelper.getErrorResponse("verifyCode错误");
		}
		Map<String, String> result = userService.switchRegisteredUser(userId, jsonMap.get("phoneNumber"), jsonMap.get("password"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/userchecklogin/{phoneNumber}/{passwordAfterSalt}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> checkLogin(@PathVariable("phoneNumber") String phoneNumber, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt) {
		Map<String, String> result = userService.checkLogin(phoneNumber, passwordAfterSalt);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("读取错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/usersessiontoken/{authToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> getSessionToken(@PathVariable("authToken") String authToken) {
		String sessionToken = userService.getSessionToken(authToken);
		if (sessionToken == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("sessionToken", sessionToken);
		return result;
	}
	
	@RequestMapping(value = "/usersetpassword/{userId}/{passwordAfterSalt}/{password}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setPassword(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("userId") Long userId, 
			@PathVariable("passwordAfterSalt") String passwordAfterSalt, 
			@PathVariable("password") String password) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (userService.checkPassword(userId, passwordAfterSalt)) {
			Map<String, String> result = userService.setPassword(userId, password);
			if (result == null) {
				return ResponseStatusHelper.getErrorResponse("写入错误");
			}
			return result;
		}
		return ResponseStatusHelper.getErrorResponse("password错误");
	}
	
	@RequestMapping(value = "/usersetphoneNumber/{userId}/{phoneNumber}/{verifyCode}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setPhoneNumber(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("userId") Long userId, 
			@PathVariable("phoneNumber") String phoneNumber,
			@PathVariable("verifyCode") String verifyCode) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (!verifyHelper.verifyInput(phoneNumber, verifyCode)) {
			return ResponseStatusHelper.getErrorResponse("verifyCode错误");
		}
		Map<String, String> result = userService.setPhoneNumber(userId, phoneNumber);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
}
