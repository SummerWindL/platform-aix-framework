package com.platform.aix.common.datacommon.db.domain;

public class PikaiThirdPartyAuthKey {
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
}