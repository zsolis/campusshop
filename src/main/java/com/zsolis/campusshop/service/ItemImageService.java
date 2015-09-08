package com.zsolis.campusshop.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class ItemImageService {
	@Autowired
	private ItemImageDAO itemImageDAO;
	@Autowired
	private ItemDAO itemDAO;
	
	public ItemImageService() {}
	
	public Map<String, String> addItemDetailImage(Long itemId, String path, Long priority) {
		Item item = itemDAO.getItemById(itemId);
		if (item == null) {
			return ResponseStatusHelper.getErrorResponse("itemId´íÎó");
		}
		Long itemImageId = itemImageDAO.addItemImage(path, item.getBarcode(), item.getName());
		ItemImage image = new ItemImage();
		image.setId(itemImageId);
		itemImageDAO.addItemDetailImage(item, image, priority);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> setItemDetailImage(Long itemImageId, String path) {
		ItemImage itemImage = itemImageDAO.getItemImageById(itemImageId);
		if (itemImage == null) {
			return ResponseStatusHelper.getErrorResponse("itemImageId´íÎó");
		}
		itemImage.setPath(path);
		return ResponseStatusHelper.getOkResponse();
	}
	
	public Map<String, String> removeItemDetailImage(Long itemId, Long itemImageId) {
		Item item = new CommonItem();
		item.setId(itemId);
		ItemImage image = new ItemImage();
		image.setId(itemImageId);
		itemImageDAO.removeItemDetailImage(item, image);
		itemImageDAO.removeItemImage(image);
		return ResponseStatusHelper.getOkResponse();
	}
}
