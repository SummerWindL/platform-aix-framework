package com.platform.aix.common.datacommon.cache.impl;

import com.cluster.platform.redis.core.RedisHashOperator;
import com.platform.aix.common.datacommon.cache.RedisOperateService;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.JedisClusterCRC16;

/**
 * @ClassName RedisOperService
 * @Description TODO
 * @Author swwan
 * @Date 2020/8/27 16:29
 * @Version 1.0
 **/
@Component
public class RedisOperateServiceImpl extends RedisHashOperator implements RedisOperateService {

    @Override
    public Long getHashLen (final String k) {
        return (Long) this.redisTemplate().execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                try {
                    return hLenDoInRedis(k, connection);
                } catch (Exception var3) {
                    refreshCache();
                    return hLenDoInRedis(k, connection);
                }
            }
        });
    }

    protected Long hLenDoInRedis(String k, RedisConnection connection) {
        Long hlen = 0L;
        RedisSerializer keySerializer = this.redisTemplate().getKeySerializer();
        byte[] rawKey = keySerializer.serialize(k);
        if (this.isCluster()) {
            int slot = JedisClusterCRC16.getSlot(rawKey);
            JedisPool pool = this.getCache().getSlotPool(slot);
            if (pool != null) {
                Jedis jedis = pool.getResource();
                hlen = jedis.hlen(rawKey);
                jedis.close();
            }
        } else {
            Jedis jedis = (Jedis)connection.getNativeConnection();
            hlen = jedis.hlen(rawKey);
        }

        return hlen;
    }
}
