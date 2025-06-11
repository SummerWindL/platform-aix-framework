package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiThirdPartyAuthInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;

public class PikaiThirdPartyAuthKey  extends BaseEntity implements Serializable, PikaiThirdPartyAuthInterface, Cloneable, KeyMethodInterface<String> {
    private String provider;

    private String providerUserId;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider == null ? null : provider.trim();
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId == null ? null : providerUserId.trim();
    }

    @Override
    public String getId() {
        return this.providerUserId;
    }

    @Override
    public void setId(String id) {
        this.providerUserId = id;
    }
}