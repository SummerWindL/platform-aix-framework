package com.platform.aix.service.redisservice.entity;

import java.io.Serializable;

/**
 * 基础结构
 * @author Advance
 * @date 2022年05月20日 15:02
 * @since V1.0.0
 */
public interface IBusiness extends Serializable {
    /**
     * get the Redis key
     *
     * @return Redis key
     * @see
     * @since 1.0
     */
    String getKey();

    /**
     * get the Redis hash key
     *
     * @return Redis hash key
     * @see
     * @since 1.0
     */
    String getField();
}
