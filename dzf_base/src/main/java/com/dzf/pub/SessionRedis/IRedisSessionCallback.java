package com.dzf.pub.SessionRedis;

import redis.clients.jedis.Jedis;

public interface IRedisSessionCallback {
Object exec(Jedis jedis);
}
