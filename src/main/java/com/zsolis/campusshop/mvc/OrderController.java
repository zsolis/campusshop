package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.service.*;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/ordersbyuser/{userId}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getOrdersByUser(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return orderService.getOrdersByUser(userId, page);
	}
	
	@RequestMapping(value = "/ordersbyuserorderstatus/{userId}/{status}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getOrdersByUserOrderStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("status") String statusString, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		OrderStatus status = OrderStatus.valueOf(statusString);
		if (status == null) {
			return null;
		}
		return orderService.getOrdersByUserOrderStatus(userId, status, page);
	}
	
	@RequestMapping(value = "/ordersbystore/{storeId}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getOrdersByStore(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return null;
		}
		return orderService.getOrdersByStore(storeId, page);
	}
	
	@RequestMapping(value = "/ordersbystoreorderstatus/{storeId}/{status}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getOrdersByStoreOrderStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("storeId") Long storeId, 
			@PathVariable("status") String statusString, 
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenId("Store", storeId, sessionToken)) {
			return null;
		}
		OrderStatus status = OrderStatus.valueOf(statusString);
		if (status == null) {
			return null;
		}
		return orderService.getOrdersByStoreOrderStatus(storeId, status, page);
	}
	
	@RequestMapping(value = "/addorder/{userId}/{source}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addOrder(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("source") String source, 
			@RequestBody Map<String, Object> jsonMap) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Boolean fromCart = null;
		if (source.equals("cart")) {
			fromCart = true;
		} else if (source.equals("direct")){
			fromCart = false;
		} else {
			return ResponseStatusHelper.getErrorResponse("source错误");
		}
		if (jsonMap.get("itemsString") == null) {
			return ResponseStatusHelper.getErrorResponse("itemsString错误");
		}
		Set<Map<String, Long>> itemIds = parseItemIds((String)jsonMap.get("itemsString"));
		Map<String, String> result = orderService.addOrder((String)jsonMap.get("orderType"), fromCart, userId, (Long)jsonMap.get("storeId"), (Long)jsonMap.get("addressId"), itemIds, (String)jsonMap.get("userNote"));
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/changeorderstatus/{orderId}/{status}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> changeOrderStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("orderId") Long orderId, 
			@PathVariable("status") String statusString) {
		if (!sessionTokenHelper.checkSessionTokenExist(sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		OrderStatus status = OrderStatus.valueOf(statusString);
		if (status == null) {
			return ResponseStatusHelper.getErrorResponse("status错误");
		}
		Map<String, String> result = orderService.changeOrderStatus(orderId, status);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/orderstatuslogs/{orderId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getOrderStatusLogs(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("orderId") Long orderId) {
		if (!sessionTokenHelper.checkSessionTokenExist(sessionToken)) {
			return null;
		}
		return orderService.getOrderStatusLogs(orderId);
	}
	
	/**
	 * @param itemsString "11:1_14:3_67:1"
	 * @return
	 * 解析商品与数量对应的字符串
	 */
	private Set<Map<String, Long>> parseItemIds(String itemsString) {
		Set<Map<String, Long>> itemIds = new HashSet<Map<String, Long>>();
		String [] groups = itemsString.split("_");
		for(String group : groups) {
			String [] splits = group.split(":");
			Map<String, Long> itemId = new HashMap<String, Long>();
			itemId.put("itemId", Long.parseLong(splits[0]));
			itemId.put("quantity", Long.parseLong(splits[1]));
			itemIds.add(itemId);
		}
		return itemIds;
	}
}
