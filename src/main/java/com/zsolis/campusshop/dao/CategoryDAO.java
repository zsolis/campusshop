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
	 * ����ID���ط���
	 */
	public Category getCategoryById(Long categoryId) {
		Session session = getSession();
		return (Category)session.get(Category.class, categoryId);
	}
	
	/**
	 * @return
	 * ����ȫ������
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		Session session = getSession();
		Query query = session.createQuery("from Category c");
		return query.list();
	}
	
	/**
	 * @return
	 * ����У԰��������Ʒ�����б�
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
	 * �����Ʒ����
	 */
	public Long addCategory(String name) {
		Category category = new Category(name);
		Session session = getSession();
		return (Long)session.save(category);
	}
}
