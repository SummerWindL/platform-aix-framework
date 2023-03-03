package com.platform.aix.common.constants;

/**
 * Redis存储Key枚举
 * @author Advance
 * @date 2022年05月20日 15:20
 * @since V1.0.0
 */
public enum RedisKeyEnum {
    /**
     * MQ指令信息推送-Redis主键
     */
    MQ指令信息推送(
            "RSK{MQ}COMMAND"
    ),
    /**
     * 指标参数配置-Redis主键
     */
    指标参数配置(
            "RSK{INDICATOR}PARAM"
    ),
    /**
     * 指标参数值-Redis主键
     */
    指标参数值(
            "RSK{VALUE}:"
    );
    private String key;

    private RedisKeyEnum(String key) {
        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
