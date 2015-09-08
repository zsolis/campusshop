package com.zsolis.campusshop.dao;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.ConstantUtil;
import com.zsolis.campusshop.util.CryptUtil;

import java.util.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAO extends DAO {
	@Autowired
	private CryptUtil cryptUtil;
	
	public StoreDAO() {}
	
	/**
	 * @return
	 * 根据商店返回storeMap
	 */
	public Map<String, Object> getStoreMapByStore(Store store) {
		Map<String, Object> storeMap = new HashMap<String, Object>();
		storeMap.put("id", store.getId());
		storeMap.put("account", store.getAccount());
		storeMap.put("name", store.getName());
		storeMap.put("address", store.getAddress());
		storeMap.put("description", store.getDescription());
		storeMap.put("statement", store.getStatement());
		storeMap.put("minimunAmount", store.getMinimumAmount());
		storeMap.put("deliveryFee", store.getDeliveryFee());
		storeMap.put("phoneNumber", store.getPhoneNumber());
		storeMap.put("image", store.getImagePath());
		return storeMap;
	}
	
	/**
	 * @return
	 * 根据ID返回商家
	 */
	public Store getStoreById(Long storeId) {
		Session session = getSession();
		return (Store)session.get(Store.class, storeId);
	}
	
	/**
	 * @return
	 * 根据账号返回商家
	 */
	public Store getStoreByAccount(String account) {
		Session session = getSession();
		Query query = session.createQuery("from Store s where s.account = :account")
				.setString("account", account);
		return (Store)query.uniqueResult();
	}
	
	/**
	 * @return
	 * 根据authToken返回商家
	 */
	public Store getStoreByAuthToken(String authToken) {
		Session session = getSession();
		Query query = session.createQuery("from Store s where s.authToken = :authToken")
				.setString("authToken", authToken);
		return (Store)query.uniqueResult();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(c.store.id as id, c.store.name as name, c.store.description as description, c.store.imagePath as imagePath)
	 * 返回服务该校园的店铺，按优先级排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getStoresByCampusOrderedByPriority(Campus campus, CampusStatus status, int page) {
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("select new map(c.store.id as id, c.store.name as name, c.store.description as description, c.store.imagePath as imagePath) from CampusStore c where c.campus = :campus and c.status = :status order by c.priority asc")
					.setEntity("campus", campus)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("select new map(c.store.id as id, c.store.name as name, c.store.description as description, c.store.imagePath as imagePath) from CampusStore c where c.campus = :campus order by c.priority asc")
					.setEntity("campus", campus)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return
	 * 返回用户购物车商品所属的店铺
	 */
	@SuppressWarnings("unchecked")
	public List<Store> getUserCartItemStores(User user) {
		Session session = getSession();
		Query query = session.createQuery("select distinct u.item.store from UserCartItem u where u.user = :user")
				.setEntity("user",user);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(u.store.id as id, u.store.name as name, u.store.description as description, u.store.imagePath as imagePath)
	 * 返回用户收藏的店铺，按收藏时间排序
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUserFavoriteStoresOrderedByDate(User user, int page) {
		Session session = getSession();
		Query query = session.createQuery("select new map(u.store.id as id, u.store.name as name, u.store.description as description, u.store.imagePath as imagePath) from UserFavoriteStore u where u.user = :user order by u.date desc")
				.setEntity("user", user)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
		return query.list();
	}
	
	/**
	 * 添加用户收藏店铺
	 */
	public void addUserFavoriteStore(User user, Store store) {
		UserFavoriteStore userFavoriteStore = new UserFavoriteStore(user, store);
		Session session = getSession();
		session.save(userFavoriteStore);
	}
	
	/**
	 * 删除用户收藏店铺
	 */
	public void removeUserFavoriteStore(User user, Store store) {
		UserFavoriteStore userFavoriteStore = new UserFavoriteStore(user, store);
		Session session = getSession();
		session.delete(userFavoriteStore);
	}
	
	/**
	 * @return
	 * 判断商家能否为该校园服务
	 */
	public boolean checkCampusArrival(Campus campus, Store store) {
		Session session = getSession();
		Query query = session.createQuery("select c.priority from CampusStore c where c.campus = :campus and c.store = :store and c.status = 1")
				.setEntity("campus", campus)
				.setEntity("store", store);
		int count = query.getFetchSize();
		if(count == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return
	 * 添加商家，保证account唯一
	 */
	public Long addStore(String account, String password, String name, String address, String phoneNumber) {
		Store store = new Store(account, password, phoneNumber, name, address);
		Session session = getSession();
		Long storeId = (Long)session.save(store);
		Long timestamp = new Date().getTime();
		store.setAuthToken(cryptUtil.encryptSHA1(storeId.toString() + timestamp.toString()));
		return storeId;
	}
}
