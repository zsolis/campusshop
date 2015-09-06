package com.zsolis.campusshop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class CampusRegionService {
	@Autowired
	private CampusRegionDAO campusRegionDAO;
	
	public CampusRegionService() {}
	
	public List<Map<String, Object>> getCampusRegionsByCampus(Long campusId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return campusRegionDAO.getRegionsByCampus(campus);
	}
	
	public Long addCampusRegion(Long campusId, String name, String description) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return campusRegionDAO.addCampusRegion(campus, name, description);
	}
	
	public void setCampusRegion(Long campusRegionId, String name, String description) {
		CampusRegion campusRegion = campusRegionDAO.getCampusRegionById(campusRegionId);
		if(campusRegion == null) {
			return;
		}
		if (name != null) {
			campusRegion.setName(name);
		}
		if (description != null) {
			campusRegion.setDescription(description);
		}
	}
	
	public void removeCampusRegion(Long campusRegionId) {
		CampusRegion campusRegion = campusRegionDAO.getCampusRegionById(campusRegionId);
		if(campusRegion == null) {
			return;
		}
		campusRegion.setStatus(CampusRegionStatus.deleted);
	}
}
