package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.*;

@Repository
public class AdministratorDAO extends DAO {
	@Autowired
	private CryptUtil cryptUtil;
	
	public AdministratorDAO() {}
	
	/**
	 * @param campusAdministrator
	 * @return
	 * 返回Map，应确保输入对象已持久化
	 */
	public Map<String, Object> getCampusAdministratorMapByCampusAdministrator(CampusAdministrator administrator) {
		Map<String, Object> administratorMap = new HashMap<String, Object>();
		administratorMap.put("id", administrator.getId());
		administratorMap.put("account", administrator.getAccount());
		administratorMap.put("name", administrator.getName());
		administratorMap.put("phoneNumber", administrator.getPhoneNumber());
		return administratorMap;
	}
	
	/**
	 * @return
	 * 返回校园管理员列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusAdministrators() {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.id as id, c.account as account, c.name as name, c.phoneNumber as phoneNumber) from CampusAdministrator c");
		return query.list();
	}
	
	/**
	 * @return
	 * 根据ID返回管理员
	 */
	public Administrator getAdministratorById(Long administratorId) {
		Session session = getSession();
		return (Administrator)session.get(Administrator.class, administratorId);
	}
	
	/**
	 * @return
	 * 根据账号返回管理员
	 */
	public Administrator getAdministratorByAccount(String account) {
		Session session = getSession();
		Query query = session.createQuery("from Administrator a where a.account = :account")
				.setString("account", account);
		return (Administrator)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 根据authToken返回管理员
	 */
	public Administrator getAdministratorByAuthToken(String authToken) {
		Session session = getSession();
		Query query = session.createQuery("from Administrator a where a.authToken = :authToken")
				.setString("authToken", authToken);
		return (Administrator)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 添加校园管理员，应确保account唯一
	 */
	public Long addCampusAdministrator(String account, String password, String name, String phoneNumber) {
		CampusAdministrator campusAdministrator = new CampusAdministrator(account, password, name, phoneNumber);
		Session session = getSession();
		Long administratorId = (Long)session.save(campusAdministrator);
		Long timestamp = new Date().getTime();
		campusAdministrator.setAuthToken(cryptUtil.encryptSHA1(administratorId.toString() + timestamp.toString()));
		return administratorId;
	}
	
	/**
	 * @return
	 * 添加超级管理员，应确保account唯一
	 */
	public Long addSuperAdministrator(String account, String password, String name) {
		SuperAdministrator administrator = new SuperAdministrator(account, password, name);
		Session session = getSession();
		Long administratorId = (Long)session.save(administrator);
		Long timestamp = new Date().getTime();
		administrator.setAuthToken(cryptUtil.encryptSHA1(administratorId.toString() + timestamp.toString()));
		return administratorId;
	}
	
	/**
	 * @return
	 * 获得超级管理员
	 */
	public SuperAdministrator getSuperAdministrator() {
		Session session = getSession();
		Query query = session.createQuery("from SuperAdministrator");
		return (SuperAdministrator)query.uniqueResult();
	}
}