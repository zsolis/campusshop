package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class AdministratorImageController {
	@Autowired
	private AdministratorImageService administratorImageService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/campuscarouselimages/{campusId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusCarouselImagesByCampus(@PathVariable("campusId") Long campusId) {
		return administratorImageService.getCampusCarouselImagesByCampus(campusId);
	}
	
	@RequestMapping(value = "/campusadimages/{campusId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusAdImagesByCampus(@PathVariable("campusId") Long campusId) {
		return administratorImageService.getCampusAdImagesByCampus(campusId);
	}
	
	@RequestMapping(value = "/administratorimages/{administratorId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getAdministratorImages(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("administratorId") Long administratorId) {
		if (!sessionTokenHelper.checkSessionTokenId("Admin", administratorId, sessionToken)) {
			return null;
		}
		return administratorImageService.getAdministratorImages(administratorId);
	}
	
	@RequestMapping(value = "/addadministratorimage/{administratorId}/{path}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addAdministratorImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("administratorId") Long administratorId, 
			@PathVariable("path") String path) {
		if (!sessionTokenHelper.checkSessionTokenId("Admin", administratorId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (administratorImageService.addAdministratorImage(administratorId, path) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setadministratorimage/{administratorImageId}/{path}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setAdministratorImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("administratorImageId") Long administratorImageId, 
			@PathVariable("path") String path) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorImageService.setAdministratorImage(administratorImageId, path);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/addcampusrecommendimage/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampusRecommendImage(@PathVariable("sessionToken") String sessionToken,
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorImageService.addCampusRecommendImage((Long)jsonMap.get("campusId"), (Long)jsonMap.get("administratorImageId"), (Long)jsonMap.get("location"), (String)jsonMap.get("url"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/removecampusrecommendimage/{campusId}/{administratorImageId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeCampusRecommendImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("administratorImageId") Long administratorImageId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorImageService.removeCampusRecommendImage(campusId, administratorImageId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setcampusrecommendimage/{campusId}/{administratorImageId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCampusRecommendImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("administratorImageId") Long administratorImageId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = administratorImageService.setCampusRecommendImage(campusId, administratorImageId, (Long)jsonMap.get("location"), (String)jsonMap.get("url"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
