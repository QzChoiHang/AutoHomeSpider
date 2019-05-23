package com.ch.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public class RedisFlushAllDB {
	Jedis jedis = new RedisConnection().jedis;
	ShardedJedis shardedJedis = new RedisConnection().shardedJedis;

	/**
	 * 增加字符串
	 */
	private void AddString() {
		// 清空数据
		System.out.println("已清空Redis中所选库的数据：" + jedis.flushDB());

	}

	public static void main(String[] args) {

		RedisFlushAllDB rs = new RedisFlushAllDB();
		rs.AddString();
	}
}
