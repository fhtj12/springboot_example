package com.example.web.db.redis;

import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class DefaultJedisPoolConfig {

    public static JedisPoolConfig create(RedisHostConfig redisHostConfig) throws Exception {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisHostConfig.getMaxConnection());
        jedisPoolConfig.setMaxWait(Duration.ofMillis(IRedisConstants.DEFAULT_WAIT_TIMEOUT_MILLIS));
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTime(Duration.ofMillis(60000));
        jedisPoolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(30000));
        jedisPoolConfig.setNumTestsPerEvictionRun(-1);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxIdle(redisHostConfig.getMaxConnection()); // maxTotal 값과 같게 설정하는 것이 최대 성능
        jedisPoolConfig.setMinIdle(5);
        return jedisPoolConfig;
    }

}
