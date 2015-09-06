package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class ItemService {
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private ItemImageDAO itemImageDAO;
	
	public ItemService() {}
	
	public List<Map<String, Object>> getCampusRecommendItems(Long campusId, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return itemDAO.getCampusRecommendItemsOrderedByPriority(campus, ItemStatus.normal, page);
	}
	
	public List<Map<String, Object>> getUserCartItems(Long userId) {
		User user = new TemporaryUser();
		user.setId(userId);
		List<Store> stores = storeDAO.getUserCartItemStores(user);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(Store store : stores) {
			Map<String, Object> storeMap = new HashMap<String, Object>();
			storeMap.put("id", store.getId());
			storeMap.put("name", store.getName());
			List<Map<String, Object>> items = itemDAO.getUserCartItemsByStore(user, store);
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("store", store);
			group.put("items", items);
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getUserFavoriteItems(Long userId, int page) {
		User user = new TemporaryUser();
		user.setId(userId);
		return itemDAO.getUserFavoriteItemsOrderedByDate(user, page);
	}
	
	public List<Map<String, Object>> getItemsByCampusOrdered(Long campusId, String orderType, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return itemDAO.getItemsByCampusOrdered(campus, orderType, page);
	}
	
	public List<Map<String, Object>> getItemsByCategoryCampusOrdered(Long categoryId, Long campusId, String orderType, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		Category category = new Category();
		category.setId(categoryId);
		return itemDAO.getItemsByCategoryCampusOrdered(category, campus, orderType, page);
	}
	
	public List<Map<String, Object>> getItemsByCampusSearchOrdered(Long campusId, String searchString, String orderType, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		return itemDAO.getItemsByCampusSearchOrdered(campus, searchString, orderType, page);
	}
	
	public List<Map<String, Object>> getStoreRecommendItems(Long storeId, int page) {
		Store store = new Store();
		store.setId(storeId);
		return itemDAO.getStoreRecommendItemsOrderedByPriority(store, ItemStatus.normal, page);
	}
	
	public List<Map<String, Object>> getItemsByStoreOrdered(Long storeId, String orderType, int page) {
		Store store = new Store();
		store.setId(storeId);
		return itemDAO.getItemsByStoreOrdered(store, orderType, ItemStatus.normal, page);
	}
	
	public List<Map<String, Object>> getItemsByCategoryStoreOrdered(Long categoryId, Long storeId, String orderType, int page) {
		Store store = new Store();
		store.setId(storeId);
		Category category = new Category();
		category.setId(categoryId);
		return itemDAO.getItemsByCategoryStoreOrdered(category, store, orderType, ItemStatus.normal, page);
	}
	
	public List<Map<String, Object>> getItemsByTypeStoreOrdered(String itemTypeString, Long storeId, String orderType, ItemStatus status, int page) {
		Store store = new Store();
		store.setId(storeId);
		return itemDAO.getItemsByTypeStoreOrdered(itemTypeString, store, orderType, status, page);
	}
	
	public List<Map<String, Object>> getItemsByStoreSearchOrdered(Long storeId, String searchString, String orderType, int page) {
		Store store = new Store();
		store.setId(storeId);
		return itemDAO.getItemsByStoreSearchOrdered(store, searchString, orderType, ItemStatus.normal, page);
	}
	
	public Map<String, Object> getItemWithCommentCount(Long itemId) {
		Item item = itemDAO.getItemById(itemId);
		Map<String, Object> itemMap = itemDAO.getItemMapByItem(item);
		itemMap.put("itemDetailImages", itemImageDAO.getImagesByItemOrderedByPriority(item));
		itemMap.put("commentCount", commentDAO.getCommentCountByItem(item));
		return itemMap;
	}
	
	public Map<String, Object> getItem(Long itemId) {
		Item item = itemDAO.getItemById(itemId);
		Map<String, Object> itemMap = itemDAO.getItemMapByItem(item);
		itemMap.put("itemDetailImages", itemImageDAO.getImagesByItemOrderedByPriority(item));
		return itemMap;
	}
	
	public void addUserCartItem(Long userId, Long itemId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.addUserCartItem(user, item);
	}
	
	public void removeUserCartItem(Long userId, Long itemId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.removeUserCartItem(user, item);
	}
	
	public void addUserFavoriteItem(Long userId, Long itemId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.addUserFavoriteItem(user, item);
	}
	
	public void removeUserFavoriteItem(Long userId, Long itemId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.removeUserFavoriteItem(user, item);
	}
	
	public Long addItem(String itemTypeString, Long categoryId, Long storeId, String name, Float presentPrice, Long stock, String brief, String detail, String barcode, Float originalPrice, Long limit) {
		Category category = new Category();
		category.setId(categoryId);
		Store store = new Store();
		store.setId(storeId);
		if(itemTypeString.equals("CommonItem")) {
			return itemDAO.addCommonItem(name, barcode, brief, detail, stock, presentPrice, category, store);
		} else if(itemTypeString.equals("PromotionItem")) {
			return itemDAO.addPromotionItem(name, barcode, brief, detail, stock, presentPrice, category, store, limit, originalPrice);
		} else if(itemTypeString.equals("GroupItem")) {
			return itemDAO.addGroupItem(name, barcode, brief, detail, stock, presentPrice, category, store, originalPrice);
		}
		return null;
	}
	
	public void addStoreRecommendItem(Long storeId, Long itemId, Long priority) {
		Store store = new Store();
		store.setId(storeId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.addStoreRecommendItem(store, item, priority);
	}
	
	public void removeStoreRecommendItem(Long storeId, Long itemId) {
		Store store = new Store();
		store.setId(storeId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.removeStoreRecommendItem(store, item);
	}
	
	public void setStoreRecommendItem(Long storeId, Long itemId, Long priority) {
		Store store = new Store();
		store.setId(storeId);
		Item item = new CommonItem();
		item.setId(itemId);
		StoreRecommendItem storeRecommendItem = itemDAO.getStoreRecommendItem(store, item);
		storeRecommendItem.setPriority(priority);
	}
	
	public void removeItem(Long itemId) {
		Item item = itemDAO.getItemById(itemId);
		item.setStatus(ItemStatus.deleted);
	}
	
	public void setItem(String newTypeString, Long itemId, Long categoryId, String name, Float presentPrice, Long stock, String brief, String detail, String barcode, ItemStatus status, Float originalPrice, Long limit) {
		Item item = itemDAO.getItemById(itemId);
		if(categoryId != null) {
			Category category = new Category();
			category.setId(categoryId);
			item.setCategory(category);
		}
		if(name != null) {
			item.setName(name);
		}
		if(presentPrice != null) {
			item.setPresentPrice(presentPrice);
		}
		if(stock != null) {
			item.setStock(stock);
		}
		if(brief != null) {
			item.setBrief(brief);
		}
		if(detail != null) {
			item.setDetail(detail);
		}
		if(barcode != null) {
			item.setBarcode(barcode);
		}
		if(status != null) {
			item.setStatus(status);
		}
		if(newTypeString.equals("CommonItem")) {
			itemDAO.switchCommonItem(itemId);
		} else if (newTypeString.equals("GroupItem")) {
			itemDAO.switchGroupItem(itemId, originalPrice);
		} else if (newTypeString.equals("PromotionItem")) {
			itemDAO.switchPromotionItem(itemId, originalPrice, limit);
		}
	}
	
	public void addItemMainImage(Long itemId, String path) {
		Long itemImageId = itemImageDAO.addItemImage(path, null, null);
		ItemImage itemImage = new ItemImage();
		itemImage.setId(itemImageId);
		Item item = itemDAO.getItemById(itemId);
		if (item == null) {
			return;
		}
		item.setMainImage(itemImage);
	}
	
	public void setItemMainImage(Long itemId, String path) {
		Item item = itemDAO.getItemById(itemId);
		if (item == null) {
			return;
		}
		ItemImage itemImage = item.getMainImage();
		itemImage.setPath(path);
	}
	
	public void addCampusRecommendItem(Long campusId, Long itemId, Long priority) {
		Campus campus = new Campus();
		campus.setId(campusId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.addCampusRecommendItem(campus, item, priority);
	}
	
	public void removeCampusRecommendItem(Long campusId, Long itemId) {
		Campus campus = new Campus();
		campus.setId(campusId);
		Item item = new CommonItem();
		item.setId(itemId);
		itemDAO.removeCampusRecommendItem(campus, item);
	}
	
	public void setCampusRecommendItem(Long campusId, Long itemId, Long priority) {
		Campus campus = new Campus();
		campus.setId(campusId);
		Item item = new CommonItem();
		item.setId(itemId);
		CampusRecommendItem campusRecommendItem = itemDAO.getCampusRecommendItem(campus, item);
		campusRecommendItem.setPriority(priority);
	}
}
