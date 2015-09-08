package com.zsolis.campusshop.dao;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.*;

import java.util.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends DAO {
	@Autowired
	private CryptUtil cryptUtil;
	
	public UserDAO() {}
	
	/**
	 * @return
	 * 根据User返回userMap，不访问数据库
	 */
	public Map<String, Object> getUserMapByUser(User user) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", user.getId());
		userMap.put("name", user.getName());
		String userType = "TemporaryUser";
		if(user instanceof RegisteredUser) {
			userType = "RegisteredUser";
			userMap.put("phoneNumber", ((RegisteredUser) user).getPhoneNumber());
		}
		userMap.put("type", userType);
		return userMap;
	}
	
	/**
	 * @return
	 * 返回用户根据ID
	 */
	public User getUserById(Long userId) {
		Session session = getSession();
		return (User)session.get(User.class, userId);
	}
	
	/**
	 * @return
	 * 根据手机号返回注册用户
	 * 无则返回null
	 */
	public RegisteredUser getUserByPhoneNumber(String phoneNumber) {
		Session session = getSession();
		Query query = session.createQuery("from RegisteredUser r where r.phoneNumber = :phoneNumber")
				.setString("phoneNumber", phoneNumber);
		return (RegisteredUser)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 根据authToken返回用户
	 * 没有则返回null
	 */
	public User getUserByAuthToken(String authToken) {
		Session session = getSession();
		Query query = session.createQuery("from User u where u.authToken = :authToken")
				.setString("authToken", authToken);
		return (User)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 添加注册用户，调用前检查phoneNumber独一
	 * 返回USER_ID
	 */
	public Long addRisteredUser(String phoneNumber, String password, Campus campus) {
		RegisteredUser user = new RegisteredUser(phoneNumber, password);
		user.setCampus(campus);
		Session session = getSession();
		Long userId = (Long)session.save(user);
		Long timestamp = new Date().getTime();
		user.setAuthToken(cryptUtil.encryptSHA1(userId.toString() + timestamp.toString()));
		return userId;
	}
	
	/**
	 * @return
	 * 添加临时用户
	 * 返回USER_ID
	 */
	public Long addTemporaryUser(Campus campus) {
		TemporaryUser user = new TemporaryUser();
		user.setCampus(campus);
		Session session = getSession();
		Long userId = (Long)session.save(user);
		Long timestamp = new Date().getTime();
		user.setAuthToken(cryptUtil.encryptSHA1(userId.toString() + timestamp.toString()));
		return userId;
	}
	
	/**
	 * @return
	 * 将临时用户转换成注册用户
	 * 返回1表示成功
	 */
	public int switchRegisteredUser(Long userId, String phoneNumber, String password) {
		Session session = getSession();
		Query query = session.createSQLQuery("update GLM_USER set DTYPE='RegisteredUser', PHONENUMBER=':phoneNumber', PASSWORD=':password' where ID=:id")
				.setLong("id", userId)
				.setString("phoneNumber", phoneNumber)
				.setString("password", password);
		return query.executeUpdate();
	}
}
