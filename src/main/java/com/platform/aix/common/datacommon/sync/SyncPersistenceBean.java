package com.platform.aix.common.datacommon.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncPersistenceBean implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SyncPersistenceBean.class);
    private ISync sync;

    public SyncPersistenceBean() {

    }

    public ISync getSync() {
        return sync;
    }

    public void setSync(ISync sync) {
        this.sync = sync;
    }

    public SyncPersistenceBean clone() {
        try {
            return (SyncPersistenceBean) super.clone();
        } catch (Exception e) {
            logger.warn("clone error.{}", e);
            return new SyncPersistenceBean();
        }
    }
}