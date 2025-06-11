package com.platform.aix.service.pikai.weixin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月11日 9:33
 */
public interface IWeixinMPService {

    /**
     * 获取二维码
     * @author fuyanliang
     * @date 2025/6/11 9:38
     * @param ticket
     * @return java.lang.String
     */
    String getQrCode(String ticket);
}
