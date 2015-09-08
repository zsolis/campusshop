package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.service.*;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/cities", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<City> getCities() {
		return cityService.getCities();
	}
	
	@RequestMapping(value = "/addcity/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCity(@PathVariable("sessionToken") String sessionToken, 
			@RequestBody Map<String, String> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Long result = cityService.addCity(jsonMap.get("name"), jsonMap.get("province"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setcity/{cityId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCity(@PathVariable("sessionToken") String sessionToken, 
			@PathVariable("cityId") Long cityId, 
			@RequestBody Map<String, String> jsonMap) {
		if (sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (cityService.setCity(cityId, jsonMap.get("name"), jsonMap.get("province")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
