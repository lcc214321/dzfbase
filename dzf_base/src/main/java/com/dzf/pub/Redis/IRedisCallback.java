package com.dzf.pub.Redis;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author Administrator
 *
 */
public interface IRedisCallback {
	//这里的jedis可能为空。所以实现方法一定要做为空校验
Object exec(Jedis jedis);
}
