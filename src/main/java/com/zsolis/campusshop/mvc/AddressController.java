package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class AddressController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/addressesbyuser/{userId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getAddressesByUser(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return addressService.getAddressesByUser(userId);
	}
	
	@RequestMapping(value = "/defaultaddress/{userId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getUserDefaultAddress(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return addressService.getUserDefaultAddress(userId);
	}
	
	@RequestMapping(value = "/addaddress/{userId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addAddress(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (addressService.addAddress(userId, (Long)jsonMap.get("campusId"), (Long)jsonMap.get("campusRegionId"), (String)jsonMap.get("detail"), (String)jsonMap.get("phoneNumber"), (String)jsonMap.get("name")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setaddress/{addressId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setAddress(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("addressId") Long addressId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("User", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = addressService.setAddress(addressId, (Long)jsonMap.get("campusId"), (Long)jsonMap.get("campusRegionId"), (String)jsonMap.get("detail"), (String)jsonMap.get("phoneNumber"), (String)jsonMap.get("name"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeaddress/{addressId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeAddress(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("addressId") Long addressId) {
		if (!sessionTokenHelper.checkSessionTokenRole("User", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = addressService.removeAddress(addressId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
