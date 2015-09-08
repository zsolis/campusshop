package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class ItemImageDAO extends DAO{
	public ItemImageDAO() {}
	
	/**
	 * @return
	 * 根据ID返回商品图片
	 */
	public ItemImage getItemImageById(Long itemImageId) {
		Session session = getSession();
		return (ItemImage)session.get(ItemImage.class, itemImageId);
	}
	
	/**
	 * @return
	 * 返回商品的所有图片
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getImagesByItemOrderedByPriority(Item item) {
		Session session = getSession();
		Query query = session.createQuery("select new map(i.image.id as id, i.image.path as path) from ItemDetailImage i where i.item = :item order by i.priority desc")
				.setEntity("item",item);
		return query.list();
	}
	
	/**
	 * @return
	 * 保存商品图片
	 */
	public Long addItemImage(String path, String barcode, String description) {
		ItemImage itemImage = new ItemImage(path, barcode, description);
		Session session = getSession();
		return (Long)session.save(itemImage);
	}
	
	/**
	 * 删除商品图片
	 */
	public void removeItemImage(ItemImage itemImage) {
		Session session = getSession();
		session.delete(itemImage);
	}
	
	/**
	 * 添加商品详情图
	 */
	public void addItemDetailImage(Item item, ItemImage image, Long priority) {
		ItemDetailImage itemDetailImage = new ItemDetailImage(item, image, priority);
		Session session = getSession();
		session.save(itemDetailImage);
	}
	
	/**
	 * 删除商品详情图
	 */
	public void removeItemDetailImage(Item item, ItemImage image) {
		ItemDetailImage itemDetailImage = new ItemDetailImage(item, image, 0L);
		Session session = getSession();
		session.delete(itemDetailImage);
	}
}
