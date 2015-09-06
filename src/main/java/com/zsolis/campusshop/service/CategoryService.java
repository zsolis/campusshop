package com.zsolis.campusshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;
	
	public CategoryService() {}
	
	public List<Category> getCategories() {
		return categoryDAO.getCategories();
	}
	
	public List<Category> getCategoriesByCampus(Long campusId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return categoryDAO.getCategoriesByCampus(campus);
	}
	
	public Long addCategory(String name) {
		return categoryDAO.addCategory(name);
	}
	
	public void setCategory(Long categoryId, String name) {
		Category category = categoryDAO.getCategoryById(categoryId);
		if (category == null) {
			return;
		}
		category.setName(name);
	}
}
