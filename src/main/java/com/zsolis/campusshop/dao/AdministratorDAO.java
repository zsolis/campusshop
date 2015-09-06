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
	 * ����Map��Ӧȷ����������ѳ־û�
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
	 * ����У԰����Ա�б�
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusAdministrators() {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.id as id, c.account as account, c.name as name, c.phoneNumber as phoneNumber) from CampusAdministrator c");
		return query.list();
	}
	
	/**
	 * @return
	 * ����ID���ع���Ա
	 */
	public Administrator getAdministratorById(Long administratorId) {
		Session session = getSession();
		return (Administrator)session.get(Administrator.class, administratorId);
	}
	
	/**
	 * @return
	 * �����˺ŷ��ع���Ա
	 */
	public Administrator getAdministratorByAccount(String account) {
		Session session = getSession();
		Query query = session.createQuery("from Administrator a where a.account = :account")
				.setString("account", account);
		return (Administrator)query.uniqueResult();
	}
	
	/**
	 * @return
	 * ����authToken���ع���Ա
	 */
	public Administrator getAdministratorByAuthToken(String authToken) {
		Session session = getSession();
		Query query = session.createQuery("from Administrator a where a.authToken = :authToken")
				.setString("authToken", authToken);
		return (Administrator)query.uniqueResult();
	}
	
	/**
	 * @return
	 * ���У԰����Ա��Ӧȷ��accountΨһ
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
	 * ��ӳ�������Ա��Ӧȷ��accountΨһ
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
	 * ��ó�������Ա
	 */
	public SuperAdministrator getSuperAdministrator() {
		Session session = getSession();
		Query query = session.createQuery("from SuperAdministrator");
		return (SuperAdministrator)query.uniqueResult();
	}
}