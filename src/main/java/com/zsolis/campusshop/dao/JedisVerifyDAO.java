package com.zsolis.campusshop.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zsolis.campusshop.util.*;

import redis.clients.jedis.*;

@Component
public class JedisVerifyDAO {
	private String keyName = "verify";
	@Autowired
	private JedisUtil jedisUtil;
	
	public JedisVerifyDAO() {}
	
	public void setVerifyCode(String phoneNumber, String verifyCode) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		Long timestamp = new Date().getTime();
		jedis.hset(keyName, phoneNumber, verifyCode + "_" + timestamp.toString());
		jedis.close();
	}
	
	public String getVerifyCode(String phoneNumber) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		String mixed = jedis.hget(keyName, phoneNumber);
		if(mixed == null) {
			return null;
		}
		String [] splits = mixed.split("_");
		if(new Date().getTime() - Long.parseLong(splits[1]) > 3600000) {
			return null;
		}
		jedis.close();
		return splits[0];
	}
}
