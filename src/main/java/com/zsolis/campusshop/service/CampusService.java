package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class CampusService {
	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private CampusDAO campusDAO;
	@Autowired
	private AdministratorDAO administratorDAO;
	
	public CampusService() {}

	public List<Map<String, Object>> getCampusesWithCity() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<City> cities = cityDAO.getCities();
		for(City city : cities) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, String>> campuses = campusDAO.getCampusesByCity(city, CampusStatus.normal);
			map.put("city", city);
			map.put("campuses", campuses);
			result.add(map);
		}
		return result;
	}
	
	public List<Map<String, Object>> getCampusesByStore(Long storeId) {
		Store store = new Store();
		store.setId(storeId);
		return campusDAO.getCampusByStore(store);
	}
	
	public void addCampusStore(Long storeId, Long campusId) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		campusDAO.addCampusStore(campus, store, 0L);
	}
	
	public void removeCampusStore(Long storeId, Long campusId) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		campusDAO.removeCampusStore(campus, store);
	}
	
	public void changeCampusStoreStatus(Long storeId, Long campusId, CampusStatus status) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		CampusStore campusStore = campusDAO.getCampusStore(campus, store);
		campusStore.setStatus(status);
	}
	
	public Long addCampus(Long cityId, Long administratorId, String name, String description) {
		City city = new City();
		city.setId(cityId);
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		return campusDAO.addCampus(administrator, city, name, description);
	}
	
	public void setCampus(Long campusId, Long cityId, String name, String description) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return;
		}
		if (cityId != null) {
			City city = new City();
			city.setId(cityId);
			campus.setCity(city);
		}
		if (name != null) {
			campus.setName(name);
		}
		if (description != null) {
			campus.setDescription(description);
		}
	}
	
	public void changeCampusStatus(Long campusId, CampusStatus status) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return;
		}
		campus.setStatus(status);
	}
	
	public void deleteCampus(Long campusId) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return;
		}
		SuperAdministrator administrator = administratorDAO.getSuperAdministrator();
		campus.setAdministrator(administrator);
	}
	
	public void changeCampusAdministrator(Long campusId, Long administratorId) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return;
		}
		Administrator administrator = administratorDAO.getAdministratorById(administratorId);
		if (administrator == null) {
			return;
		}
		campus.setAdministrator(administrator);
	}
}
