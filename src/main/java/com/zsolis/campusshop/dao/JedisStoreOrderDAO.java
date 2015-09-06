package com.zsolis.campusshop.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.zsolis.campusshop.util.JedisUtil;

import redis.clients.jedis.Jedis;

public class JedisStoreOrderDAO {
	private String keyName = "storeOrder";
	@Autowired
	private JedisUtil jedisUtil;
	
	public JedisStoreOrderDAO() {}
	
	public void addStoreOrder(Long storeId) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		jedis.hincrBy(keyName, storeId.toString(), 1L);
		jedis.close();
	}
	
	public void removeStoreOrder(Long storeId) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		if(!jedis.hexists(keyName, storeId.toString())) {
			jedis.hset(keyName, storeId.toString(), "1");
		}
		jedis.hincrBy(keyName, storeId.toString(), -1L);
		jedis.close();
	}
	
	public Long getStoreOrderCount(Long storeId) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		String count = jedis.hget(keyName, storeId.toString());
		if(count == null) {
			return 0L;
		}
		jedis.close();
		return Long.parseLong(count);
	}
}
