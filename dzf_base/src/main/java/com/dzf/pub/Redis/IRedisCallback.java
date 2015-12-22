package com.dzf.pub.Redis;

import redis.clients.jedis.Jedis;

public interface IRedisCallback {
Object exec(Jedis jedis);
}
