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
	@Autowired
	private UserDAO userDAO;
	
	public AddressService() {}
	
	public List<Map<String, Object>> getAddressesByUser(Long userId) {
		User user = new TemporaryUser();
		user.setId(userId);
		return addressDAO.getAddressesByUser(user);
	}
	
	public Map<String, Object> getUserDefaultAddress(Long userId) {
		//³Ö¾Ã»¯user
		User user = userDAO.getUserById(userId);
		return addressDAO.getUserDefaultAddress(user);
	}
	
	public Long addAddress(Long userId, Long campusId, Long campusRegionId, String detail, String phoneNumber, String name) {
		User user = new TemporaryUser();
		user.setId(userId);
		Campus campus = new Campus();
		campus.setId(campusId);
		CampusRegion campusRegion = new CampusRegion();
		campusRegion.setId(campusRegionId);
		return addressDAO.addAddress(user, campus, campusRegion, detail, phoneNumber, name);
	}
	
	public Map<String, String> setAddress(Long addressId, Long campusId, Long campusRegionId, String detail, String phoneNumber, String name) {
		Address address = addressDAO.getAddressById(addressId);
		if (address == null) {
			return ResponseStatusHelper.getErrorResponse("addressId´íÎó");
		}
		if (campusId != null) {
			Campus campus = new Campus();
			campus.setId(campusId);
			address.setCampus(campus);
		}
		if (campusRegionId != null) {
			CampusRegion campusRegion = new CampusRegion();
			campusRegion.setId(campusRegionId);
			address.setCampusRegion(campusRegion);
		}
		if (detail != null) {
			address.setDetail(detail);
		}
		if (phoneNumber != null) {
			address.setPhoneNumber(phoneNumber);
		}
		if (name != null) {
			address.setName(name);
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> removeAddress(Long addressId) {
		Address address = addressDAO.getAddressById(addressId);
		if (address == null) {
			return ResponseStatusHelper.getErrorResponse("addressId´íÎó");
		}
		address.setStatus(AddressStatus.deleted);
		return ResponseStatusHelper.getOkResponse();
	}
}
