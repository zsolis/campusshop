package com.zsolis.campusshop.mvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.ItemStatus;
import com.zsolis.campusshop.service.*;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/campusrecommenditems/{campusId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusRecommendItems(@PathVariable("campusId") Long campusId, 
			@PathVariable("page") int page) {
		return itemService.getCampusRecommendItems(campusId, page);
	}
	
	@RequestMapping(value = "/usercartitems/{userId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getUserCartItems(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return itemService.getUserCartItems(userId);
	}
	
	@RequestMapping(value = "/userfavoriteitems/{userId}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getUserFavoriteItems(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return itemService.getUserFavoriteItems(userId, page);
	}
	
	@RequestMapping(value = "/itemsbycampus/{campusId}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByCampusOrdered(@PathVariable("campusId") Long campusId, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByCampusOrdered(campusId, orderType, page);
	}
	
	@RequestMapping(value = "/itemsbycategorycampus/{categoryId}/{campusId}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByCategoryCampusOrdered(@PathVariable("categoryId") Long categoryId, 
			@PathVariable("campusId") Long campusId, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByCategoryCampusOrdered(categoryId, campusId, orderType, page);
	}
	
	@RequestMapping(value = "/itemsbycampussearch/{campusId}/{search}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByCampusSearchOrdered(@PathVariable("campusId") Long campusId, 
			@PathVariable("search") String searchString, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByCampusSearchOrdered(campusId, searchString, orderType, page);
	}
	
	@RequestMapping(value = "/storerecommenditems/{storeId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getStoreRecommendItems(@PathVariable("storeId") Long storeId, 
			@PathVariable("page") int page) {
		return itemService.getStoreRecommendItems(storeId, page);
	}
	
	@RequestMapping(value = "/itemsbystore/{storeId}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByStoreOrdered(@PathVariable("storeId") Long storeId, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByStoreOrdered(storeId, orderType, page);
	}
	
	@RequestMapping(value = "/itemsbycategorystore/{categoryId}/{storeId}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByCategoryStoreOrdered(@PathVariable("categoryId") Long categoryId, 
			@PathVariable("storeId") Long storeId, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByCategoryStoreOrdered(categoryId, storeId, orderType, page);
	}
	
	@RequestMapping(value = "/itemsbystoresearch/{storeId}/{search}/{orderType}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByStoreSearchOrdered(@PathVariable("storeId") Long storeId, 
			@PathVariable("search") String searchString, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		return itemService.getItemsByStoreSearchOrdered(storeId, searchString, orderType, page);
	}
	
	@RequestMapping(value = "/itemsbytypestore/{storeId}/{itemType}/{status}/{orderType}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getItemsByTypeStoreOrdered(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("itemType") String itemTypeString, 
			@PathVariable("status") String statusString, 
			@PathVariable("orderType") String orderType, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return null;
		}
		ItemStatus status = ItemStatus.valueOf(statusString);
		return itemService.getItemsByTypeStoreOrdered(itemTypeString, storeId, orderType, status, page);
	}
	
	@RequestMapping(value = "/itemwithcommentcount/{itemId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getItemWithCommentCount(@PathVariable("itemId") Long itemId) {
		return itemService.getItemWithCommentCount(itemId);
	}
	
	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getItem(@PathVariable("itemId") Long itemId) {
		return itemService.getItem(itemId);
	}
	
	@RequestMapping(value = "/addusercartitem/{userId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addUserCartItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.addUserCartItem(userId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeusercartitem/{userId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeUserCartItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.removeUserCartItem(userId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/adduserfavoriteitem/{userId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addUserFavoriteItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.addUserFavoriteItem(userId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeuserfavoriteitem/{userId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeUserFavoriteItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.removeUserFavoriteItem(userId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/additem/{storeId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> addItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (itemService.addItem((String)jsonMap.get("itemTypeString"), (Long)jsonMap.get("categoryId"), storeId, (String)jsonMap.get("name"), 
				(Float)jsonMap.get("presentPrice"), (Long)jsonMap.get("stock"), (String)jsonMap.get("brief"), (String)jsonMap.get("detail"), 
				(String)jsonMap.get("barcode"), (Float)jsonMap.get("originalPrice"), (Long)jsonMap.get("limit")) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/addstorerecommenditem/{storeId}/{itemId}/{priority}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addStoreRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("itemId") Long itemId, 
			@PathVariable("priority") Long priority) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.addStoreRecommendItem(storeId, itemId, priority);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removestorerecommenditem/{storeId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeStoreRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.removeStoreRecommendItem(storeId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setstorerecommenditem/{storeId}/{itemId}/{priority}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setStoreRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("itemId") Long itemId, 
			@PathVariable("priority") Long priority) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.setStoreRecommendItem(storeId, itemId, priority);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removeitem/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.removeItem(itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setitem/{itemId}/{sessionToken}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> setItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.setItem((String)jsonMap.get("itemTypeString"), itemId, (Long)jsonMap.get("categoryId"), (String)jsonMap.get("name"), 
				(Float)jsonMap.get("presentPrice"), (Long)jsonMap.get("stock"), (String)jsonMap.get("brief"), (String)jsonMap.get("detail"), 
				(String)jsonMap.get("barcode"), ItemStatus.valueOf((String)jsonMap.get("statusString")), (Float)jsonMap.get("originalPrice"), (Long)jsonMap.get("limit"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/additemmainimage/{itemId}/{path}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addItemMainImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId, 
			@PathVariable("path") String path) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.addItemMainImage(itemId, path);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setitemmainimage/{itemId}/{path}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setItemMainImage(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("itemId") Long itemId, 
			@PathVariable("path") String path) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.setItemMainImage(itemId, path);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/addcampusrecommenditem/{campusId}/{itemId}/{priority}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCampusRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("itemId") Long itemId, 
			@PathVariable("priority") Long priority) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.addCampusRecommendItem(campusId, itemId, priority);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/setcampusrecommenditem/{campusId}/{itemId}/{priority}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCampusRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("itemId") Long itemId, 
			@PathVariable("priority") Long priority) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.setCampusRecommendItem(campusId, itemId, priority);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
	
	@RequestMapping(value = "/removecampusrecommenditem/{campusId}/{itemId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> removeCampusRecommendItem(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId, 
			@PathVariable("itemId") Long itemId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = itemService.removeCampusRecommendItem(campusId, itemId);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
