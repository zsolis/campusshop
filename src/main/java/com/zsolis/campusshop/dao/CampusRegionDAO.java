package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class CampusRegionDAO extends DAO{
	public CampusRegionDAO() {}
	
	/**
	 * @return
	 * ����ID����У԰����
	 */
	public CampusRegion getCampusRegionById(Long campusRegionId) {
		Session session = getSession();
		return (CampusRegion)session.get(CampusRegion.class, campusRegionId);
	}
	
	/**
	 * @return
	 * ����У԰ӵ�е�����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRegionsByCampus(Campus campus) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.id as id, c.name as name, c.description as description) from CampusRegion c where c.campus = :campus and c.status = 0")
				.setEntity("campus",campus);
		return query.list();
	}
	
	/**
	 * @return
	 * ���У԰����
	 */
	public Long addCampusRegion(Campus campus, String name, String description) {
		CampusRegion campusRegion = new CampusRegion(name, description, campus);
		Session session = getSession();
		return (Long)session.save(campusRegion);
	}
}
