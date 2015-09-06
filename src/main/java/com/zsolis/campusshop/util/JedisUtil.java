package com.zsolis.campusshop.util;

import org.springframework.stereotype.Component;

import redis.clients.jedis.*;

@Component
public class JedisUtil {
	public JedisPool jedisPool;
	
	public JedisUtil() {
		jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
	}
		
	public JedisPool getJedisPool() {
		return jedisPool;
	}
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
