package com.example.web.db.redis;

import com.example.web.model.ProtoMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class RedisConnector {

    private final Class<?> caller;
    private JedisPoolConfig jedisPoolConfig;

    private final RedisHostConfig redisHostConfig;

    /**
     * key : redis 관련 class
     */
    private static final Map<Class<?>, JedisPool> jedisPool = new HashMap<>();

    public RedisConnector(Config config) throws Exception {
        this.caller = config.callerClass;
        this.jedisPoolConfig = config.jedisPoolConfig;
        this.redisHostConfig = config.redisHostConfig;

        if (this.jedisPoolConfig == null) {
            this.jedisPoolConfig = DefaultJedisPoolConfig.create(this.redisHostConfig); // default
        }
    }

    public static class Config {
        private final Class<?> callerClass;
        private JedisPoolConfig jedisPoolConfig = null;

        // 각 자식 클래스에서 초기화
        private RedisHostConfig redisHostConfig;

        public Config(Class<?> callerClass) {
            this.callerClass = callerClass;
        }

        public Config setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
            this.jedisPoolConfig = jedisPoolConfig;
            return this;
        }

        public Config setHostConfig(String host, int port, int dbNum, int maxConnection) {
            this.redisHostConfig = new RedisHostConfig(host, port, dbNum, maxConnection);
            return this;
        }

        public Config setHostConfig(String host, int port, int dbNum, int maxConnection, String password) {
            this.redisHostConfig = new RedisHostConfig(host, port, dbNum, maxConnection, password);
            return this;
        }

        public Config setHostConfig(String hostPropKey, String portPropKey, String dbNumPropKey
                , String maxConnectionPropKey) throws Exception {
            this.redisHostConfig = new RedisHostConfig(hostPropKey, portPropKey, dbNumPropKey, maxConnectionPropKey);
            return this;
        }

        public Config setHostConfig(String hostPropKey, String portPropKey, String dbNumPropKey
                , String maxConnectionPropKey, String passwordPropKey) throws Exception {
            this.redisHostConfig = new RedisHostConfig(hostPropKey, portPropKey, dbNumPropKey, maxConnectionPropKey, passwordPropKey);
            return this;
        }
    }

    private JedisPool getJedisPool() {
        JedisPool retJedisPool;

        retJedisPool = jedisPool.get(this.caller);
        if (retJedisPool == null) {
            retJedisPool = createJedisPool();
            jedisPool.put(this.caller, retJedisPool);
        }

        return retJedisPool;
    }

    private synchronized JedisPool createJedisPool() {
        return new JedisPool(this.jedisPoolConfig
                , redisHostConfig.getHost()
                , redisHostConfig.getPort()
                , IRedisConstants.DEFAULT_READ_TIMEOUT_MILLIS
                , this.redisHostConfig.isAuth() ? this.redisHostConfig.getPassword() : null
                , redisHostConfig.getDbNum());
    }

    protected Jedis getJedis() {
        return getJedisPool().getResource();
    }

    protected String createDataKey(String namespace, String key) {
        return namespace + ":" + key;
    }

    private ProtoMap parseData(String jsonStr) throws Exception {
        if (jsonStr == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<ProtoMap> typeReference = new TypeReference<>() {
        };

        return objectMapper.readValue(jsonStr, typeReference);
    }

    /**
     * 특정 인스턴스에서 데이터 하나만 가져오기
     * 세션뿐만이 아니라 다양한 곳에서 Redis를 활용하기 위해 별도로 만든 메서드.
     *
     * @param namespace Redis 네임스페이스
     * @param key       Redis 데이터의 key
     */
    public ProtoMap getData(String namespace, String key) throws Exception {
        ProtoMap outMap;

        String data;
        try (Jedis jedis = getJedis()) {
            data = jedis.get(createDataKey(namespace, key));
            outMap = parseData(data);
        }

        return outMap;
    }

    private void updateData(String namespace, String key, ProtoMap updateData, int expire, boolean expireYn) throws Exception {
        if (key == null || key.isEmpty()) {
            return;
        }

        try (Jedis jedis = getJedis()) {
            if (expireYn) {
                jedis.setex(createDataKey(namespace, key), expire, new JSONObject(updateData).toString());
            } else {
                jedis.set(createDataKey(namespace, key), new JSONObject(updateData).toString());
            }
        }
    }

    /**
     * 특정 인스턴스에서 데이터 변경
     *
     * @param namespace  Redis 네임스페이스
     * @param key        Redis 데이터의 key
     * @param updateData 업데이트할 데이터
     */
    public void updateData(String namespace, String key, ProtoMap updateData) throws Exception {
        updateData(namespace, key, updateData, 0, false);
    }

    /**
     * 특정 인스턴스에서 데이터 변경 (expire)
     *
     * @param namespace  Redis 네임스페이스
     * @param key        Redis 데이터의 key
     * @param updateData 업데이트할 데이터
     * @param expire     expire 시간(초)
     */
    public void updateExpireData(String namespace, String key, ProtoMap updateData, int expire) throws Exception {
        updateData(namespace, key, updateData, expire, true);
    }
}
