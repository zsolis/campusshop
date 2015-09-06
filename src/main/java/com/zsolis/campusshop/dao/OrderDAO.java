package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.ConstantUtil;

@Repository
public class OrderDAO extends DAO {
	public OrderDAO() {}
	
	/**
	 * @return
	 * ��Order����orderMap�����������ݿ�
	 */
	public Map<String, Object> getOrderMapByOrder(Order order) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("storeId", order.getStore().getId());
		orderMap.put("storeName", order.getStore().getName());
		orderMap.put("usreId", order.getUser().getId());
		orderMap.put("userName", order.getUser().getName());
		orderMap.put("address", order.getAddress().getFinalAddress());
		orderMap.put("userNote", order.getUserNote());
		orderMap.put("storeNote", order.getStoreNote());
		orderMap.put("orderId", order.getId());
		orderMap.put("status", order.getStatus());
		orderMap.put("date", order.getDate());
		orderMap.put("deliveryFee", order.getDeliveryFee());
		orderMap.put("totalPrice", order.getTotalPrice());
		String orderType = "";
		if(order instanceof AlipayDirectOrder) {
			orderType = "AlipayDirectOrder";
		} else if(order instanceof CashOrder) {
			orderType = "CashOrder";
		}
		orderMap.put("type", orderType);
		return orderMap;
	}
	
	/**
	 * @return
	 * ����ID���ض���
	 */
	public Order getOrderById(Long orderId) {
		Session session = getSession();
		return (Order)session.get(Order.class, orderId);
	}
	
	/**
	 * @param status
	 * @param page
	 * @return
	 * ���ظõ���ȫ������������ʱ�併������
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByStoreOrderedByDateDesc(Store store, int page) {
		Session session = getSession();
		Query query = session.createQuery("from Order o where o.store = :store order by o.date desc")
				.setEntity("store", store)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @param status
	 * @param page
	 * @return
	 * ���ظõ���ĳ��״̬�Ķ���������ʱ�併������
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByStoreOrderStatusOrderedByDateDesc(Store store, OrderStatus status, int page) {
		Session session = getSession();
		Query query = session.createQuery("from Order o where o.store = :store and o.status = :status order by o.date desc")
				.setEntity("store", store)
				.setInteger("status", status.ordinal())
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @param status
	 * @param page
	 * @return
	 * ���ظ��û�ȫ����������ʱ�併������
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByUserOrderedByDateDesc(User user, int page) {
		Session session = getSession();
		Query query = session.createQuery("from Order o where o.user = :user order by o.date desc")
				.setEntity("user", user)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @param status
	 * @param page
	 * @return
	 * ���ظ��û�ĳ��״̬�Ķ�������ʱ�併������
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByUserOrderStatusOrderedByDateDesc(User user, OrderStatus status, int page) {
		Session session = getSession();
		Query query = session.createQuery("from Order o where o.user = :user and o.status = :status order by o.date desc")
				.setEntity("user", user)
				.setInteger("status", status.ordinal())
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @return
	 * ����ֽ𶩵������ض�����
	 */
	public Long addCashOrder(User user, Store store, Address address, Set<Map<String, Object>> items, 
			String userNote, Float deliveryFee, Float totalPrice) {
		Session session = getSession();
		CashOrder order = new CashOrder(address, user, store, userNote, null, OrderStatus.beforeAccept);
		order.setDeliveryFee(deliveryFee);
		order.setTotalPrice(totalPrice);
		Long orderId = (Long)session.save(order);
		for(Map<String, Object> itemMap : items) {
			Item item = (Item)itemMap.get("item");
			Long quantity = (Long)itemMap.get("quantity");
			OrderItem orderItem = new OrderItem(order, item, quantity);
			session.save(orderItem);
		}
		return orderId;
	}
	
	/**
	 * @return
	 * ���֧����ֱ�ӵ��˶��������ض�����
	 */
	public Long addAlipayDirectOrder(User user, Store store, Address address, Set<Map<String, Object>> items, 
			String userNote, Float deliveryFee, Float totalPrice) {
		Session session = getSession();
		AlipayDirectOrder order = new AlipayDirectOrder(address, user, store, userNote, null, OrderStatus.beforePay);
		order.setDeliveryFee(deliveryFee);
		order.setTotalPrice(totalPrice);
		Long orderId = (Long)session.save(order);
		for(Map<String, Object> itemMap : items) {
			Item item = (Item)itemMap.get("item");
			Long quantity = (Long)itemMap.get("quantity");
			OrderItem orderItem = new OrderItem(order, item, quantity);
			session.save(orderItem);
		}
		return orderId;
	}
}
