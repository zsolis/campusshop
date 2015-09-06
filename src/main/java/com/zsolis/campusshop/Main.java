package com.zsolis.campusshop;

//import java.util.*;

//import com.zsolis.campusshop.service.*;

//import com.zsolis.campusshop.util.*;
//
//import redis.clients.jedis.*;

import org.hibernate.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.HibernateUtil;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HibernateUtil hibernateUtil = new HibernateUtil();
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
//		Administrator administrator = new CampusAdministrator("23456789", "777888999", "章朝", "122222222222222");
//		City city = new City("石家庄", "河北");
//		session.save(administrator);
//		session.save(city);
//		Campus campus = new Campus("中南大学", "中南大学", city);
//		campus.setAdministrator(administrator);
//		campus.setStatus(CampusStatus.normal);
//		session.save(campus);
//		System.out.println();
		
		Query query = session.createQuery("from Campus c where c.status = :status")
				.setInteger("status", CampusStatus.normal.ordinal());
		int size = query.list().size();
		System.out.println(size);
		transaction.commit();
		session.close();
		
//		JedisPool jedisPool = new JedisUtil().getJedisPool();
//		Jedis jedis = null;
//		try {
//			jedis = jedisPool.getResource();
//			jedis.set("foo", "bar");
//			String fooBar = jedis.get("foo");
//			System.out.println(fooBar);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//		jedisPool.destroy();
		
//		QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
//		String uploadToken = qiniuUtil.getUploadToken();
//		System.out.println(uploadToken);
		
//		SMSUtil smsUtil = new SMSUtil();
//		smsUtil.sendSMS("15100200868", "这是一个测试哦551022，from 章阳【购了么】");
		
//		CryptHelper cryptHelper = new CryptHelper();
//		System.out.println(cryptHelper.getSalt());
//		String encrypt = cryptHelper.encryptSHA1("password123TTAA");
//		System.out.println(encrypt);
//		String afterSalt = cryptHelper.encryptSHA1(encrypt + cryptHelper.getSalt());
//		System.out.println(afterSalt);
//		boolean result = cryptHelper.comparePassword(afterSalt, encrypt);
//		System.out.println(result);
		
//		System.out.println(new Date().getTime());
	}
}
