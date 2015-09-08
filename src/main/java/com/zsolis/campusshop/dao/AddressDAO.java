package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class AddressDAO extends DAO{
	public AddressDAO() {}
	
	/**
	 * @return
	 * 根据ID返回地址
	 */
	public Address getAddressById(Long addressId) {
		Session session = getSession();
		return (Address)session.get(Address.class, addressId);
	}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(id, finalAddress)
	 * 返回用户保存的地址
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAddressesByUser(User user) {
		Session session = getSession();
		Query query = session.createQuery("from Address a where a.user = :user and a.status = 0")
				.setEntity("user",user);
		List<Address> addresses = query.list();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(Address address : addresses) {
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("id", address.getId());
			addressMap.put("finalAddress", address.getFinalAddress());
			result.add(addressMap);
		}
		return result;
	}
	
	/**
	 * @return
	 * 返回用户默认地址，没有默认地址的返回第一个地址
	 * 参数用户必须持久化，不可使用引用
	 */
	public Map<String, Object> getUserDefaultAddress(User user) {
		Address address = user.getDefaultAddress();
		Map<String, Object> addressMap = new HashMap<String, Object>();
		if(address != null) {
			addressMap.put("id", address.getId());
			addressMap.put("finalAddress", address.getFinalAddress());
		} else {
			Session session = getSession();
			Query query = session.createQuery("from Address a where a.user = :user and a.status = 0")
					.setEntity("user", user);
			Address newAddress = (Address)query.uniqueResult();
			if (newAddress == null) {
				return null;
			}
			addressMap.put("id", newAddress.getId());
			addressMap.put("finalAddress", newAddress.getFinalAddress());
		}
		return addressMap;
	}
	
	/**
	 * @return
	 * 添加用户地址，返回ID
	 */
	public Long addAddress(User user, Campus campus, CampusRegion campusRegion, String detail, String phoneNumber, String name) {
		Address address = new Address(campus, campusRegion, detail, phoneNumber, name, user);
		Session session = getSession();
		return (Long)session.save(address);
	}
}
