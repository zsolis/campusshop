package com.zsolis.campusshop.service;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class AdministratorImageService {
	@Autowired
	private AdministratorImageDAO administratorImageDAO;
	
	public AdministratorImageService() {}
	
	public List<Map<String, Object>> getCampusCarouselImagesByCampus(Long campusId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return administratorImageDAO.getCampusCarouselImagesOrderedByLocation(campus);
	}
	
	public List<Map<String, Object>> getCampusAdImagesByCampus(Long campusId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return administratorImageDAO.getCampusAdImagesOrderedByLocation(campus);
	}
	
	public List<Map<String, Object>> getAdministratorImages(Long administratorId) {
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		return administratorImageDAO.getAdministratorImages(administrator);
	}
	
	public Long addAdministratorImage(Long administratorId, String path) {
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		return administratorImageDAO.addAdministratorImage(administrator, path);
	}
	
	public Map<String, String> setAdministratorImage(Long administratorImageId, String path) {
		AdministratorImage administratorImage = administratorImageDAO.getAdministratorImageById(administratorImageId);
		if (administratorImage == null) {
			return ResponseStatusHelper.getErrorResponse("administratorImageId´íÎó");
		}
		administratorImage.setPath(path);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> addCampusRecommendImage(Long campusId, Long administratorImageId, Long location, String url) {
		Campus campus = new Campus();
		campus.setId(campusId);
		AdministratorImage administratorImage = new AdministratorImage();
		administratorImage.setId(administratorImageId);
		administratorImageDAO.addCampusRecommendImage(campus, administratorImage, location, url);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> removeCampusRecommendImage(Long campusId, Long administratorImageId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		AdministratorImage administratorImage = new AdministratorImage();
		administratorImage.setId(administratorImageId);
		administratorImageDAO.removeCampusRecommendImage(campus, administratorImage);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> setCampusRecommendImage(Long campusId, Long administratorImageId, Long location, String url) {
		Campus campus = new Campus();
		campus.setId(campusId);
		AdministratorImage administratorImage = new AdministratorImage();
		administratorImage.setId(administratorImageId);
		CampusRecommendImage campusRecommendImage = administratorImageDAO.getCampusRecommendImage(campus, administratorImage);
		if (campusRecommendImage == null) {
			return ResponseStatusHelper.getErrorResponse("input´íÎó");
		}
		if (location != null) {
			campusRecommendImage.setLocation(location);
		}
		if (url != null) {
			campusRecommendImage.setUrl(url);
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
