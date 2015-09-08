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
	
	public Map<String, String> addCampusStore(Long storeId, Long campusId) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		campusDAO.addCampusStore(campus, store, 0L);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> removeCampusStore(Long storeId, Long campusId) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		campusDAO.removeCampusStore(campus, store);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> changeCampusStoreStatus(Long storeId, Long campusId, CampusStatus status) {
		Store store = new Store();
		store.setId(storeId);
		Campus campus = new Campus();
		campus.setId(campusId);
		CampusStore campusStore = campusDAO.getCampusStore(campus, store);
		if (campusStore == null) {
			return ResponseStatusHelper.getErrorResponse("input错误");
		}
		campusStore.setStatus(status);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Long addCampus(Long cityId, Long administratorId, String name, String description) {
		City city = new City();
		city.setId(cityId);
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		return campusDAO.addCampus(administrator, city, name, description);
	}
	
	public Map<String, String> setCampus(Long campusId, Long cityId, String name, String description) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return ResponseStatusHelper.getErrorResponse("campusId错误");
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
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> changeCampusStatus(Long campusId, CampusStatus status) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return ResponseStatusHelper.getErrorResponse("campusId错误");
		}
		campus.setStatus(status);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> deleteCampus(Long campusId) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return ResponseStatusHelper.getErrorResponse("campusId错误");
		}
		SuperAdministrator administrator = administratorDAO.getSuperAdministrator();
		campus.setAdministrator(administrator);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> changeCampusAdministrator(Long campusId, Long administratorId) {
		Campus campus = campusDAO.getCampusById(campusId);
		if (campus == null) {
			return ResponseStatusHelper.getErrorResponse("campusId错误");
		}
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		campus.setAdministrator(administrator);
		return ResponseStatusHelper.getOkResponse();
	}
}
