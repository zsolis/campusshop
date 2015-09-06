package com.zsolis.campusshop.dao;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;

import com.zsolis.campusshop.util.*;

public abstract class DAO {
	@Autowired
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}
	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
	
	public Session getSession() {
		return hibernateUtil.getSessionFactory().getCurrentSession();
	}
}
