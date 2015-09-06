package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.domain.*;

@Repository
public class CityDAO extends DAO {
	public CityDAO() {}

	/**
	 * @return
	 * ���س����б�
	 */
	@SuppressWarnings("unchecked")
	public List<City> getCities() {
		Session session = getSession();
		Query query = session.createQuery("from City");
		return query.list();
	}
	
	/**
	 * @return
	 * ����ID���س���
	 */
	public City getCityById(Long cityId) {
		Session session = getSession();
		return (City)session.get(City.class, cityId);
	}
	
	/**
	 * @return
	 * ��ӳ���
	 */
	public Long addCity(String name, String province) {
		City city = new City(name, province);
		Session session = getSession();
		return (Long)session.save(city);
	}
}
