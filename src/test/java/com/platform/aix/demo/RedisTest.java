package com.platform.aix.demo;

import com.alibaba.fastjson.JSONObject;
import com.cluster.platform.redis.ICache;
import com.platform.aix.PlatformAixApplicationTests;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.ClusterOperations;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Advance
 * @date 2022年02月17日 18:17
 * @since V1.0.0
 */
public class RedisTest extends PlatformAixApplicationTests {

    @Resource
    private ICache cache;

    @Test
    public void test(){
        cache.putHashValue("mytable:r6","{'222222':'5656'}","{'898239','12323212'}");
        System.out.println("插入成功");
    }

    /**
     * 集群测试
     * @author Advance
     * @date 2022/2/17 18:45
     */
    @Test
    public void testCluster(){
        ClusterOperations clusterOperations = cache.getClusterOperations();
        RedisClusterNode node = new RedisClusterNode("101.35.80.154",7005);
        Map<String,Object> map = new HashMap<>();
        map.put("1","哈哈哈哈哈");
        Set keys = clusterOperations.keys(node, "1");

        System.out.println(JSONObject.toJSONString(keys));
    }
}
