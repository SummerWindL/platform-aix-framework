package com.platform.aix.service.pikai.weixin.impl;

import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import com.platform.aix.service.pikai.weixin.IWeixinMPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月11日 9:39
 */
@Slf4j
@Service
public class WeixinMPServiceImpl implements IWeixinMPService {

    @Resource
    HttpClientService httpClientService;
    /**
     * https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGa7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAycWVfcng3SlNlaUkxcE5oTE5FMUUAAgRxBEhoAwQAjScA
     * @author fuyanliang
     * @date 2025/6/11 9:40
     * @param ticket
     * @return java.lang.String
     */
    @Override
    public String getQrCode(String ticket) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=");
            sb.append(ticket);
            Map<String, Object> params = new HashMap<String, Object>();
            HttpResponseWrapper responseWrapper = httpClientService.get(sb.toString(), params);
            byte[] imageData = responseWrapper.getResponseBody().getBytes("ISO-8859-1");
            // 转换为Base64
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            return base64Image;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
