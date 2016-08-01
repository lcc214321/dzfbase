package com.dzf.pub.SessionRedis;


import redis.clients.jedis.Transaction;

public interface ISessionTransactionCallback {
Object exec(Transaction tx);
}
