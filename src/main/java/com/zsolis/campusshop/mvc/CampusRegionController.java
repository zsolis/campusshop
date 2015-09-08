package com.zsolis.campusshop.mvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class CampusRegionController {
	@Autowired
	private CampusRegionService campusRegionService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/campusregions/{campusId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusRegionsByCampus(@PathVariable("campusId") Long campusId) {
		return campusRegionService.getCampusRegionsByCampus(campusId);
	}
	
	@RequestMapping(value = "/addcampusregion/{campusId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampusRegion(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@RequestBody Map<String, String> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (campusRegionService.addCampusRegion(campusId, jsonMap.get("name"), jsonMap.get("description")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setcampusregion/{campusRegionId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCampusRegion(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusRegionId") Long campusRegionId, 
			@RequestBody Map<String, String> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusRegionService.setCampusRegion(campusRegionId, jsonMap.get("name"), jsonMap.get("description"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/removecampusregion/{campusRegionId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeCampusRegion(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusRegionId") Long campusRegionId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusRegionService.removeCampusRegion(campusRegionId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
