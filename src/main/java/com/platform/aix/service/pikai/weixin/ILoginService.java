package com.platform.aix.service.pikai.weixin;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月10日 14:12
 */
public interface ILoginService {

    String createQrCodeTicket();

    String checkLogin(String ticket);

}
