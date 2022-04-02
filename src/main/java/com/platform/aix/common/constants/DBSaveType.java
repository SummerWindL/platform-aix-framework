package com.platform.aix.common.constants;

public enum DBSaveType {
    ASYNC("异步模式", 0),
    SYNC_BATCH("同步批量事务", 1),
    SINGLE("同步单笔", 2);

    public final int value;
    public final String name;

    DBSaveType(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
