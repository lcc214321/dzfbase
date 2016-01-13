package com.dzf.pub.Redis;


import redis.clients.jedis.Transaction;

public interface ITransactionCallback {
Object exec(Transaction tx);
}
