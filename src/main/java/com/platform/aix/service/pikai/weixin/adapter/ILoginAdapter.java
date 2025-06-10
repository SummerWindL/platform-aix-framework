package com.platform.aix.service.pikai.weixin.adapter;

import java.io.IOException;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月10日 14:14
 * 登录适配器接口
 */
public interface ILoginAdapter {

    String createQrCodeTicket() throws IOException;

}
