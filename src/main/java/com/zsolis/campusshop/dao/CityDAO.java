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
	 * 返回城市列表
	 */
	@SuppressWarnings("unchecked")
	public List<City> getCities() {
		Session session = getSession();
		Query query = session.createQuery("from City");
		return query.list();
	}
	
	/**
	 * @return
	 * 根据ID返回城市
	 */
	public City getCityById(Long cityId) {
		Session session = getSession();
		return (City)session.get(City.class, cityId);
	}
	
	/**
	 * @return
	 * 添加城市
	 */
	public Long addCity(String name, String province) {
		City city = new City(name, province);
		Session session = getSession();
		return (Long)session.save(city);
	}
}
