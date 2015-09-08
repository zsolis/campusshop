package com.zsolis.campusshop.mvc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class ItemImageController {
	@Autowired
	private ItemImageService itemImageService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/additemdetailimage/{itemId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addItemDetailImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemImageService.addItemDetailImage(itemId, (String)jsonMap.get("path"), (Long)jsonMap.get("priority"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setitemdetailimage/{itemId}/{path}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setItemDetailImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemImageId") Long itemImageId, 
			@PathVariable("path") String path) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemImageService.setItemDetailImage(itemImageId, path);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeitemdetailimage/{itemId}/{itemImageId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeItemDetailImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId, 
			@PathVariable("itemImageId") Long itemImageId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemImageService.removeItemDetailImage(itemId, itemImageId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
