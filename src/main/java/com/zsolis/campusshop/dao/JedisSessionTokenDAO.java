package com.zsolis.campusshop.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zsolis.campusshop.util.*;

import redis.clients.jedis.*;

@Component
public class JedisSessionTokenDAO {
	private String keyName = "token";
	@Autowired
	private JedisUtil jedisUtil;
	
	public JedisSessionTokenDAO() {}
	
	public void setSessionToken(String sessionToken, Long id, String role) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		Long timestamp = new Date().getTime();
		jedis.hset(keyName, sessionToken, role + "_" + id.toString() + "_" + timestamp.toString());
		jedis.close();
	}
	
	public Long getIdBySessionToken(String sessionToken, String role) {
		Jedis jedis = jedisUtil.getJedisPool().getResource();
		String mixed = jedis.hget(keyName, sessionToken);
		jedis.close();
		if(mixed == null) {
			return null;
		}
		String [] splits = mixed.split("_");
		if(!splits[0].equals(role)) {
			return null;
		}
		return Long.parseLong(splits[1]);
	}
}
