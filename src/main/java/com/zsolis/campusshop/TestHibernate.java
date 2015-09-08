package com.zsolis.campusshop;

import org.hibernate.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.HibernateUtil;

public class TestHibernate {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HibernateUtil hibernateUtil = new HibernateUtil();
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from Campus c where c.status = :status")
				.setInteger("status", CampusStatus.normal.ordinal());
		int size = query.list().size();
		System.out.println(size);
		transaction.commit();
		session.close();
	}
}
