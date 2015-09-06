package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class OrderStatusLogService {
	@Autowired
	private OrderStatusLogDAO orderStatusLogDAO;
	
	public OrderStatusLogService() {}
	
	public List<Map<String, Object>> getOrderStatusLogs(Long orderId) {
		Order order = new CashOrder();
		order.setId(orderId);
		return orderStatusLogDAO.getOrderStatusLogs(order);
	}
}
