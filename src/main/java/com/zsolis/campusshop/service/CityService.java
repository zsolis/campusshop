package com.zsolis.campusshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class CityService {
	@Autowired
	private CityDAO cityDAO;
	
	public CityService() {}
	
	public List<City> getCities() {
		return cityDAO.getCities();
	}
	
	public Long addCity(String name, String province) {
		return cityDAO.addCity(name, province);
	}
	
	public void setCity(Long cityId, String name, String province) {
		City city = cityDAO.getCityById(cityId);
		if (city == null) {
			return;
		}
		if (name != null) {
			city.setName(name);
		}
		if (province != null) {
			city.setProvince(province);
		}
	}
}
