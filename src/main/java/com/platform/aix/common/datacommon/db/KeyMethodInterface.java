package com.platform.aix.common.datacommon.db;

public interface KeyMethodInterface<PK> {
    PK getId();

    void setId(PK id);
}
