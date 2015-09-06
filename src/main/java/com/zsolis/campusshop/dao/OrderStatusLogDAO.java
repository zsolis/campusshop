package com.zsolis.campusshop.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class OrderStatusLogDAO extends DAO{
	public OrderStatusLogDAO() {}
	
	/**
	 * @return
	 * 返回该订单的状态改变记录
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrderStatusLogs(Order order) {
		Session session = getSession();
		Query query = session.createQuery("select new map(o.status as status, o.date as date) from OrderStatusLog o where o.order = :order")
				.setEntity("order",order);
		return query.list();
	}
	
	/**
	 * @return
	 * 添加订单状态变化日志
	 */
	public Long addOrderStatusLog(Order order, OrderStatus status) {
		OrderStatusLog orderStatusLog = new OrderStatusLog(order, status);
		Session session = getSession();
		return (Long)session.save(orderStatusLog);
	}
}
