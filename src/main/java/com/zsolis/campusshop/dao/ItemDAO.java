package com.zsolis.campusshop.dao;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.*;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.*;

@Repository
public class ItemDAO extends DAO {
	public ItemDAO() {}
	
	/**
	 * @return
	 * 根据Item返回itemMap，不访问数据库
	 */
	public Map<String, Object> getItemMapByItem(Item item) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("itemId", item.getId());
		itemMap.put("itemName", item.getName());
		itemMap.put("itemMainImage", item.getMainImage().getPath());
		itemMap.put("categoryId", item.getCategory().getId());
		itemMap.put("categoryName", item.getCategory().getName());
		itemMap.put("storeId", item.getStore().getId());
		itemMap.put("storeName", item.getStore().getName());
		itemMap.put("storeStatement", item.getStore().getStatement());
		itemMap.put("storeMinimumAmount", item.getStore().getMinimumAmount());
		itemMap.put("itemBrief", item.getBrief());
		itemMap.put("itemPrice", item.getPresentPrice());
		itemMap.put("itemSales", item.getSales());
		itemMap.put("itemStock", item.getStock());
		itemMap.put("itemDetail", item.getDetail());
		itemMap.put("itemDate", item.getDate());
		itemMap.put("itemBarcode", item.getBarcode());
		String itemType = "commonItem";
		if(item instanceof PromotionItem) {
			itemType = "PromotionItem";
			itemMap.put("itemOriginalPrice", ((PromotionItem) item).getOriginalPrice());
			itemMap.put("itemLimit", ((PromotionItem) item).getLimit());
		} else if(item instanceof GroupItem) {
			itemType = "GroupItem";
			itemMap.put("itemOriginalPrice", ((GroupItem) item).getOriginalPrice());
		}
		itemMap.put("itemType", itemType);
		return itemMap;
	}
	
	/**
	 * @return
	 * 根据ID返回商品
	 */
	public Item getItemById(Long itemId) {
		Session session = getSession();
		return (Item)session.get(Item.class, itemId);
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(u.item.id as itemId, u.item.name as itemName, u.item.presentPrice as itemPrice, u.quantity as quantity, u.date as date)
	 * 依据店铺返回用户购物车商品
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUserCartItemsByStore(User user, Store store) {
		Session session = getSession();
		Query query = session.createQuery("select new map(u.item.id as itemId, u.item.name as itemName, u.item.presentPrice as itemPrice, u.quantity as quantity, u.date as date) from UserCartItem u where u.user = :user and u.item.store = :store and u.item.status = 0")
				.setEntity("user",user)
				.setEntity("store",store);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>
	 * new map(o.item.id as itemId, o.item.name as itemName, o.item.presentPrice as itemCurrentPrice, o.item.date as itemCurrentDate, o.item.mainImage.path as itemImagePath, o.quantity as quantity, o.itemPrice as itemOldPrice, o.itemDate as itemOldDate)
	 * 返回该订单的商品列表，没有按商品状态筛选
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByOrder(Order order) {
		Session session = getSession();
		Query query = session.createQuery("select new map(o.item.id as itemId, o.item.name as itemName, o.item.presentPrice as itemCurrentPrice, o.item.date as itemCurrentDate, o.item.mainImage.path as itemImagePath, o.quantity as quantity, o.itemPrice as itemOldPrice, o.itemDate as itemOldDate) from OrderItem o where o.order = :order")
				.setEntity("order", order);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回店铺中指定类型的商品，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByTypeStoreOrdered(String itemTypeString, Store store, String orderType, ItemStatus status, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from " + itemTypeString + " i where i.category = :category and i.store = :store and i.status = :status " + orderString)
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from " + itemTypeString + " i where i.category = :category and i.store = :store " + orderString)
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回店铺中该分类的商品，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByCategoryStoreOrdered(Category category, Store store, String orderType, ItemStatus status, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.category = :category and i.store = :store and i.status = :status " + orderString)
					.setEntity("category", category)
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.category = :category and i.store = :store " + orderString)
					.setEntity("category", category)
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回店铺中全部商品，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByStoreOrdered(Store store, String orderType, ItemStatus status, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.store = :store and i.status = :status " + orderString)
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.store = :store " + orderString)
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回店铺搜索的商品结果，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByStoreSearchOrdered(Store store, String searchString, String orderType, ItemStatus status, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.name like '%" + searchString + "%' and i.store = :store and i.status = :status " + orderString)
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.name like '%" + searchString + "%' and i.store = :store " + orderString)
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回校园中该分类的商品，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByCategoryCampusOrdered(Category category, Campus campus, String orderType, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.category = :category and i.store.campus = :campus and i.status = 0 " + orderString)
				.setEntity("category", category)
				.setEntity("campus", campus)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回校园中全部商品，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByCampusOrdered(Campus campus, String orderType, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.store.campus = :campus and i.status = 0 " + orderString)
				.setEntity("campus", campus)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName)
	 * 返回校园搜索的商品结果，按指定方式排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getItemsByCampusSearchOrdered(Campus campus, String searchString, String orderType, int page) {
		String orderString = "";
		if(orderType == "sales") {
			orderString = "order by i.sales desc";
		} else if(orderType ==  "date") {
			orderString = "order by i.date desc";
		} else if(orderType == "price") {
			orderString = "order by i.price asc";
		}
		Session session = getSession();
		Query query = session.createQuery("select new map(i.id as itemId, i.name as itemName, i.presentPrice as itemPrice, i.mainImage.path as itemImage, i.store.id as storeId, i.store.name as storeName) from Item i where i.name like '%" + searchString + "%' and i.store.campus = :campus and i.status = 0 " + orderString)
				.setEntity("campus", campus)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(c.item.id as itemId, c.item.name as itemName, c.item.presentPrice as itemPrice, c.item.mainImage.path as itemImage, c.item.store.id as storeId, c.item.store.name as storeName)
	 * 返回校园推荐的商品，按优先级排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusRecommendItemsOrderedByPriority(Campus campus, ItemStatus status, int page) {
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(c.item.id as itemId, c.item.name as itemName, c.item.presentPrice as itemPrice, c.item.mainImage.path as itemImage, c.item.store.id as storeId, c.item.store.name as storeName) from CampusRecommendItem c where c.campus = :campus and c.item.status = :status order by c.priority desc")
					.setEntity("campus", campus)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(c.item.id as itemId, c.item.name as itemName, c.item.presentPrice as itemPrice, c.item.mainImage.path as itemImage, c.item.store.id as storeId, c.item.store.name as storeName) from CampusRecommendItem c where c.campus = :campus order by c.priority desc")
					.setEntity("campus", campus)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
		
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(s.item.id as itemId, s.item.name as itemName, s.item.presentPrice as itemPrice, s.item.mainImage.path as itemImage, s.item.store.id as storeId, s.item.store.name as storeName)
	 * 返回店铺推荐的商品，按优先级排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getStoreRecommendItemsOrderedByPriority(Store store, ItemStatus status, int page) {
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(s.item.id as itemId, s.item.name as itemName, s.item.presentPrice as itemPrice, s.item.mainImage.path as itemImage, s.item.store.id as storeId, s.item.store.name as storeName) from StoreRecommendItem s where s.store = :store and s.item.status = :status order by s.priority desc")
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(s.item.id as itemId, s.item.name as itemName, s.item.presentPrice as itemPrice, s.item.mainImage.path as itemImage, s.item.store.id as storeId, s.item.store.name as storeName) from StoreRecommendItem s where s.store = :store order by s.priority desc")
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(u.item.id as itemId, u.item.name as itemName, u.item.presentPrice as itemPrice, u.item.mainImage.path as itemImage, u.store.name as storeName)
	 * 返回用户收藏的商品，按收藏时间排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUserFavoriteItemsOrderedByDate(User user, int page) {
		Session session = getSession();
		Query query = session.createQuery("select new map(u.item.id as itemId, u.item.name as itemName, u.item.presentPrice as itemPrice, u.item.mainImage.path as itemImage, u.store.name as storeName) from UserFavoriteItem u where u.user = :user and u.item.status = 0 order by u.date desc")
				.setEntity("user", user)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * 添加用户购物车商品
	 */
	public void addUserCartItem(User user, Item item) {
		UserCartItem userCartItem = new UserCartItem(user, item, 1L);
		Session session = getSession();
		session.save(userCartItem);
	}
	
	/**
	 * 删除用户购物车商品
	 */
	public void removeUserCartItem(User user, Item item) {
		UserCartItem userCartItem = new UserCartItem(user, item, 1L);
		Session session = getSession();
		session.delete(userCartItem);
	}
	
	/**
	 * 添加用户收藏商品
	 */
	public void addUserFavoriteItem(User user, Item item) {
		UserFavoriteItem userFavoriteItem = new UserFavoriteItem(user, item);
		Session session = getSession();
		session.save(userFavoriteItem);
	}
	
	/**
	 * 删除用户收藏商品
	 */
	public void removeUserFavoriteItem(User user, Item item) {
		UserFavoriteItem userFavoriteItem = new UserFavoriteItem(user, item);
		Session session = getSession();
		session.delete(userFavoriteItem);
	}
	
	/**
	 * @return
	 * 添加普通商品，返回ID
	 */
	public Long addCommonItem(String name, String barcode, String brief, String detail, Long stock, Float presentPrice, Category category, Store store) {
		Session session = getSession();
		CommonItem item = new CommonItem(name, barcode, brief, detail, stock, presentPrice, category, store);
		return (Long)session.save(item);
	}
	
	/**
	 * @return
	 * 添加促销商品，返回ID
	 */
	public Long addPromotionItem(String name, String barcode, String brief, String detail, Long stock, Float presentPrice, Category category, Store store, Long limit, Float originalPrice) {
		Session session = getSession();
		PromotionItem item = new PromotionItem(name, barcode, brief, detail, stock, presentPrice, category, store, limit, originalPrice);
		return (Long)session.save(item);
	}
	
	/**
	 * @return
	 * 添加团购商品，返回ID
	 */
	public Long addGroupItem(String name, String barcode, String brief, String detail, Long stock, Float presentPrice, Category category, Store store, Float originalPrice) {
		Session session = getSession();
		GroupItem item = new GroupItem(name, barcode, brief, detail, stock, presentPrice, category, store, originalPrice);
		return (Long)session.save(item);
	}
	
	/**
	 * 添加商家推荐商品
	 */
	public void addStoreRecommendItem(Store store, Item item, Long priority) {
		Session session = getSession();
		StoreRecommendItem storeRecommendItem = new StoreRecommendItem(store, item, priority);
		session.save(storeRecommendItem);
	}
	
	/**
	 * 删除商家推荐商品
	 */
	public void removeStoreRecommendItem(Store store, Item item) {
		Session session = getSession();
		StoreRecommendItem storeRecommendItem = new StoreRecommendItem(store, item, 0L);
		session.delete(storeRecommendItem);
	}
	
	/**
	 * @return
	 * 返回商家推荐的商品
	 */
	public StoreRecommendItem getStoreRecommendItem(Store store, Item item) {
		Session session = getSession();
		Query query = session.createQuery("from StoreRecommendItem s where s.store = :store and s.item = :item")
				.setEntity("store", store)
				.setEntity("item", item);
		return (StoreRecommendItem)query.uniqueResult();
	}
	
	/**
	 * 添加校园推荐商品
	 */
	public void addCampusRecommendItem(Campus campus, Item item, Long priority) {
		Session session = getSession();
		CampusRecommendItem campusRecommendItem = new CampusRecommendItem(campus, item, priority);
		session.save(campusRecommendItem);
	}
	
	/**
	 * 删除校园推荐商品
	 */
	public void removeCampusRecommendItem(Campus campus, Item item) {
		Session session = getSession();
		CampusRecommendItem campusRecommendItem = new CampusRecommendItem(campus, item, 0L);
		session.delete(campusRecommendItem);
	}
	
	/**
	 * @return
	 * 返回校园推荐的商品
	 */
	public CampusRecommendItem getCampusRecommendItem(Campus campus, Item item) {
		Session session = getSession();
		Query query = session.createQuery("from CampusRecommendItem c where c.campus = :campus and c.item = :item")
				.setEntity("campus", campus)
				.setEntity("item", item);
		return (CampusRecommendItem)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 将商品类型转换为普通
	 */
	public int switchCommonItem(Long itemId) {
		Session session = getSession();
		Query query = session.createSQLQuery("update GLM_ITEM set DTYPE = 'CommonItem', ORIGINALPRICE = null, LIMITQUANTITY = null where ID = :id")
				.setLong("id", itemId);
		return query.executeUpdate();
	}
	
	/**
	 * @return
	 * 将商品类型转换为团购
	 */
	public int switchGroupItem(Long itemId, Float originalPrice) {
		Session session = getSession();
		Query query = session.createSQLQuery("update GLM_ITEM set DTYPE = 'GroupItem', ORIGINALPRICE = :originalPrice, LIMITQUANTITY = null where ID = :id")
				.setLong("id", itemId)
				.setFloat("originalPrice", originalPrice);
		return query.executeUpdate();
	}
	
	/**
	 * @return
	 * 将商品类型转换为促销
	 */
	public int switchPromotionItem(Long itemId, Float originalPrice, Long limitQuantity) {
		Session session = getSession();
		Query query = session.createSQLQuery("update GLM_ITEM set DTYPE = 'PromotionItem', ORIGINALPRICE = :originalPrice, LIMITQUANTITY = :limitQuantity where ID = :id")
				.setLong("id", itemId)
				.setFloat("originalPrice", originalPrice)
				.setLong("limitQuantity", limitQuantity);
		return query.executeUpdate();
	}
}
