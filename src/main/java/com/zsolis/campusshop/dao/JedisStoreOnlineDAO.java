package com.zsolis.campusshop.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.zsolis.campusshop.util.JedisUtil;

import redis.clients.jedis.Jedis;

public class JedisStoreOnlineDAO {
	private String keyName = "storeOnline";
	@Autowired
	private JedisUtil jedisUtil;
	
	public JedisStoreOnlineDAO() {}
	
	public void setStoreOnline(Long storeId) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		Long tiemstamp = new Date().getTime();
		jedis.hset(keyName, storeId.toString(), tiemstamp.toString());
		jedis.close();
	}
	
	public boolean checkStoreOnline(Long storeId) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		Long now = new Date().getTime();
		String last = jedis.hget(keyName, storeId.toString());
		jedis.close();
		if(last == null) {
			return false;
		}
		if(now - Long.parseLong(last) > 10000) {
			return false;
		}
		return true;
	}
}
