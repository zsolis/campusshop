package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.service.*;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}
	
	@RequestMapping(value = "/categoriesbycampus/{campusId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Category> getCategoriesByCampus(@PathVariable("campusId") Long campusId) {
		return categoryService.getCategoriesByCampus(campusId);
	}
	
	@RequestMapping(value = "/addcategory/{name}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCategory(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("name") String name) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (categoryService.addCategory(name) == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/setcategory/{categoryId}/{name}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> setCategory(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("categoryId") Long categoryId, 
			@PathVariable("name") String name) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = categoryService.setCategory(categoryId, name);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("–¥»Î¥ÌŒÛ");
		}
		return result;
	}
}
