package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class AddressService {
	@Autowired
	private AddressDAO addressDAO;
	
	public AddressService() {}
	
	public List<Map<String, Object>> getAddressesByUser(Long userId) {
		User user = new TemporaryUser();
		user.setId(userId);
		return addressDAO.getAddressesByUser(user);
	}
	
	public Map<String, Object> getUserDefaultAddress(Long userId) {
		User user = new TemporaryUser();
		user.setId(userId);
		return addressDAO.getUserDefaultAddress(user);
	}
	
	public Long addAddress(Campus campus, CampusRegion campusRegion, String detail, String phoneNumber, String name) {
		return addressDAO.addAddress(campus, campusRegion, detail, phoneNumber, name);
	}
	
	public void setAddress(Long addressId, Long campusId, Long campusRegionId, String detail, String phoneNumber, String name) {
		Address address = addressDAO.getAddressById(addressId);
		if (address == null) {
			return;
		}
		Campus campus = new Campus();
		campus.setId(campusId);
		CampusRegion campusRegion = new CampusRegion();
		campusRegion.setId(campusRegionId);
		address.setCampus(campus);
		address.setCampusRegion(campusRegion);
		address.setDetail(detail);
		address.setPhoneNumber(phoneNumber);
		address.setName(name);
	}
	
	public void removeAddress(Long addressId) {
		Address address = addressDAO.getAddressById(addressId);
		if (address == null) {
			return;
		}
		address.setStatus(AddressStatus.deleted);
	}
}
