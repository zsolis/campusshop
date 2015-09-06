package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.domain.*;

@Repository
public class CategoryDAO extends DAO {
	public CategoryDAO() {}
	
	/**
	 * @return
	 * 根据ID返回分类
	 */
	public Category getCategoryById(Long categoryId) {
		Session session = getSession();
		return (Category)session.get(Category.class, categoryId);
	}
	
	/**
	 * @return
	 * 返回全部分类
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		Session session = getSession();
		Query query = session.createQuery("from Category c");
		return query.list();
	}
	
	/**
	 * @return
	 * 返回校园包含的商品分类列表
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategoriesByCampus(Campus campus) {
		Session session = getSession();
		Query query = session.createQuery("select distinct i.category from Item i where i.store.campus = :campus")
				.setEntity("campus", campus);
		return query.list();
	}
	
	/**
	 * @return
	 * 添加商品分类
	 */
	public Long addCategory(String name) {
		Category category = new Category(name);
		Session session = getSession();
		return (Long)session.save(category);
	}
}
