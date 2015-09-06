package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.domain.*;

@Repository
public class CampusDAO extends DAO {
	public CampusDAO() {}
	
	/**
	 * @return
	 * ����ID���У԰
	 */
	public Campus getCampusById(Long campusId) {
		Session session = getSession();
		return (Campus)session.get(Campus.class, campusId);
	}
	
	/**
	 * @return List<Map<String, String>>
	 * new map(c.id as id, c.name as name, c.description as description)
	 * ���ظó��е�ѧУ
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCampusesByCity(City city, CampusStatus status) {
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(c.id as id, c.name as name, c.description as description) from Campus c where c.city = :city and c.status = :status")
					.setEntity("city",city)
					.setInteger("status", status.ordinal());
			return query.list();
		} else {
			Query query = session.createQuery("select new map(c.id as id, c.name as name, c.description as description) from Campus c where c.city = :city")
					.setEntity("city",city);
			return query.list();
		}
	}
	
	/**
	 * @return
	 * new map(c.status as status, c.id as id, c.name as name, c.description as description)
	 * ���ع���Ա��Ͻ��У԰
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusByAdministrator(Administrator administrator) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.status as status, c.id as id, c.name as name, c.description as description) from Campus c where c.administrator = :administrator")
				.setEntity("administrator", administrator);
		return query.list();
	}

	/**
	 * @return
	 * new map(c.status as status, c.campus.id as id, c.campus.name as name, c.campus.description as description)
	 * ���ظõ��̷����У԰
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusByStore(Store store) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.status as status, c.campus.id as id, c.campus.name as name, c.campus.description as description) from CampusStore c where c.store = :store")
				.setEntity("store", store);
		return query.list();
	}
	
	/**
	 * Ϊ������ӷ����У԰
	 */
	public void addCampusStore(Campus campus, Store store, Long priority) {
		CampusStore campusStore = new CampusStore(campus, store, priority);
		Session session = getSession();
		session.save(campusStore);
	}
	
	/**
	 * ɾ�����̷����У԰
	 */
	public void removeCampusStore(Campus campus, Store store) {
		CampusStore campusStore = new CampusStore(campus, store, 0L);
		Session session = getSession();
		session.delete(campusStore);
	}
	
	/**
	 * @return
	 * ���ص��̷����У԰
	 */
	public CampusStore getCampusStore(Campus campus, Store store) {
		Session session = getSession();
		Query query = session.createQuery("from CampusStore c where c.campus = :campus and c.store = :store")
				.setEntity("campus", campus)
				.setEntity("store", store);
		return (CampusStore)query.uniqueResult();
	}
	
	/**
	 * @return
	 * ���У԰
	 */
	public Long addCampus(Administrator administrator, City city, String name, String description) {
		Campus campus = new Campus(name, description, city, administrator);
		Session session = getSession();
		return (Long)session.save(campus);
	}
}
