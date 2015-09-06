package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class TestController {
	@Autowired
	private CampusService campusService;

	public CampusService getCampusService() {
		return campusService;
	}
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}
	
	@RequestMapping(value = "/campus-list-with-city", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCampusListWithCity() {
		return campusService.getCampusesWithCity();
	}
}
