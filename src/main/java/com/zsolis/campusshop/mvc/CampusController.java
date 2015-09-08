package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.CampusStatus;
import com.zsolis.campusshop.service.*;

@Controller
public class CampusController {
	@Autowired
	private CampusService campusService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/campuses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusesWithCity() {
		return campusService.getCampusesWithCity();
	}
	
	@RequestMapping(value = "/campusesbystore/{storeId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusesByStore(@PathVariable("storeId") Long storeId) {
		return campusService.getCampusesByStore(storeId);
	}
	
	@RequestMapping(value = "/addcampusstore/{storeId}/{campusId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampusStore(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("campusId") Long campusId) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusService.addCampusStore(storeId, campusId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/removecampusstore/{storeId}/{campusId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeCampusStore(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("campusId") Long campusId) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusService.removeCampusStore(storeId, campusId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/changecampusstorestatus/{storeId}/{campusId}/{status}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> changeCampusStoreStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("campusId") Long campusId, 
			@PathVariable("status") String statusString) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		CampusStatus status = CampusStatus.valueOf(statusString);
		if (status == null) {
			return ResponseStatusHelper.getErrorResponse("status错误");
		}
		Map<String, String> result = campusService.changeCampusStoreStatus(storeId, campusId, status);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/addcampus/{administratorId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("administratorId") Long administratorId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenId("Admin", administratorId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (campusService.addCampus((Long)jsonMap.get("cityId"), administratorId, (String)jsonMap.get("name"), (String)jsonMap.get("description")) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setcampus/{campusId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCampus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusService.setCampus(campusId, (Long)jsonMap.get("cityId"), (String)jsonMap.get("name"), (String)jsonMap.get("description"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/changecampusstatus/{campusId}/{status}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> changeCampusStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("status") String statusString) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		CampusStatus status = CampusStatus.valueOf(sessionToken);
		if (status == null) {
			return ResponseStatusHelper.getErrorResponse("status错误");
		}
		Map<String, String> result = campusService.changeCampusStatus(campusId, status);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/deletecampus/{campusId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> deleteCampus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusService.deleteCampus(campusId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/changecampusadministrator/{campusId}/{administratorId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> changeCampusAdministrator(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("administratorId") Long administratorId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = campusService.changeCampusAdministrator(campusId, administratorId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
}
