package com.zsolis.campusshop;

import com.zsolis.campusshop.util.*;

import redis.clients.jedis.*;

public class TestJedis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JedisPool jedisPool = new JedisUtil().getJedisPool();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("foo", "bar");
			String fooBar = jedis.get("foo");
			System.out.println(fooBar);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		jedisPool.destroy();
	}

}
