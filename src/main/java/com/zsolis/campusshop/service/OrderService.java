package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class OrderService {
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderStatusLogDAO orderStatusLongDAO;
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private AddressDAO addressDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JedisStoreOrderDAO jedisStoreOrderDAO;
	
	public OrderService() {}
	
	public List<Map<String, Object>> getOrdersByUser(Long userId, int page) {
		User user = new TemporaryUser();
		user.setId(userId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Order> orders = orderDAO.getOrdersByUserOrderedByDateDesc(user, page);
		for(Order order : orders) {
			List<Map<String, Object>> itemsMap = itemDAO.getItemsByOrder(order);
			Map<String, Object> orderMap = orderDAO.getOrderMapByOrder(order);
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("order", orderMap);
			group.put("items", itemsMap);
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getOrdersByUserOrderStatus(Long userId, OrderStatus status, int page) {
		User user = new TemporaryUser();
		user.setId(userId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Order> orders = orderDAO.getOrdersByUserOrderStatusOrderedByDateDesc(user, status, page);
		for(Order order : orders) {
			List<Map<String, Object>> itemsMap = itemDAO.getItemsByOrder(order);
			Map<String, Object> orderMap = orderDAO.getOrderMapByOrder(order);
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("order", orderMap);
			group.put("items", itemsMap);
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getOrdersByStore(Long storeId, int page) {
		Store store = new Store();
		store.setId(storeId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Order> orders = orderDAO.getOrdersByStoreOrderedByDateDesc(store, page);
		for(Order order : orders) {
			List<Map<String, Object>> itemsMap = itemDAO.getItemsByOrder(order);
			Map<String, Object> orderMap = orderDAO.getOrderMapByOrder(order);
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("order", orderMap);
			group.put("items", itemsMap);
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getOrdersByStoreOrderStatus(Long storeId, OrderStatus status, int page) {
		Store store = new Store();
		store.setId(storeId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Order> orders = orderDAO.getOrdersByStoreOrderStatusOrderedByDateDesc(store, status, page);
		for(Order order : orders) {
			List<Map<String, Object>> itemsMap = itemDAO.getItemsByOrder(order);
			Map<String, Object> orderMap = orderDAO.getOrderMapByOrder(order);
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("order", orderMap);
			group.put("items", itemsMap);
			result.add(group);
		}
		return result;
	}
	
	public Long addOrder(String orderType, Long userId, Long storeId, Long addressId, Set<Map<String, Long>> itemIds, String userNote) {
		Address address = addressDAO.getAddressById(addressId);
		Store store = storeDAO.getStoreById(storeId);
		User user = userDAO.getUserById(userId);
		if(!storeDAO.checkCampusArrival(address.getCampus(), store)){
			return null;
		}
		Set<Map<String, Object>> items = new HashSet<Map<String, Object>>();
		Float totalPrice = 0.0F;
		Float deliveryFee = 0.0F;
		for(Map<String, Long> itemIdMap : itemIds) {
			Item item = itemDAO.getItemById(itemIdMap.get("itemId"));
			if(item.getStore().getId() != storeId) {
				return null;
			}
			Long quantity = itemIdMap.get("quantity");
			totalPrice += item.getPresentPrice() * quantity;
			//添加商品销量
			item.setSales(item.getSales() + quantity);
			item.setStock(item.getStock() - quantity);
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("item", item);
			itemMap.put("quantity", quantity);
			items.add(itemMap);
		}
		if(store.getMinimumAmount() > totalPrice) {
			deliveryFee = store.getDeliveryFee();
		}
		totalPrice += deliveryFee;
		if(!user.getDefaultAddress().equals(address)) {
			user.setDefaultAddress(address);
		}
		Long orderId = null;
		if(orderType.equals("CashOrder")) {
			orderId = orderDAO.addCashOrder(user, store, address, items, userNote, deliveryFee, totalPrice);
			Order order = new CashOrder();
			order.setId(orderId);
			orderStatusLongDAO.addOrderStatusLog(order, OrderStatus.beforeAccept);
			jedisStoreOrderDAO.addStoreOrder(storeId);
		} else if(orderType.equals("AlipayDirectOrder")) {
			orderId = orderDAO.addAlipayDirectOrder(user, store, address, items, userNote, deliveryFee, totalPrice);
			Order order = new CashOrder();
			order.setId(orderId);
			orderStatusLongDAO.addOrderStatusLog(order, OrderStatus.beforePay);
		}
		return orderId;
	}
	
	public void changeOrderStatus(Long orderId, OrderStatus status) {
		Order order = orderDAO.getOrderById(orderId);
		if(order == null) {
			return;
		}
		if(order.getStatus().ordinal() >= status.ordinal()) {
			return;
		}
		if(status == OrderStatus.beforeAccept) {
			jedisStoreOrderDAO.addStoreOrder(order.getStore().getId());
		}
		if(order.getStatus() == OrderStatus.beforeAccept) {
			jedisStoreOrderDAO.removeStoreOrder(order.getStore().getId());
		}
		order.setStatus(status);
		orderStatusLongDAO.addOrderStatusLog(order, status);
	}
}
