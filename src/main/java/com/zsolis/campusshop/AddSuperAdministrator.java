package com.zsolis.campusshop;

import java.util.Date;

import org.hibernate.*;

import com.zsolis.campusshop.domain.SuperAdministrator;
import com.zsolis.campusshop.util.*;

public class AddSuperAdministrator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CryptUtil cryptUtil = new CryptUtil();
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String account = "superadmin";
		String password = cryptUtil.encryptSHA1("hehuoren");
		String name = "系统管理员";
		SuperAdministrator administrator = new SuperAdministrator(account, password, name);
		Long administratorId = (Long)session.save(administrator);
		Long timestamp = new Date().getTime();
		administrator.setAuthToken(cryptUtil.encryptSHA1(administratorId.toString() + timestamp.toString()));
		transaction.commit();
	}

}
