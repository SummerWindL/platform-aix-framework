package com.platform.aix.common.request;

import com.platform.aix.common.spring.AppService;
import org.apache.commons.codec.digest.DigestUtils;

public class CheckSign {

    private static ThirdAuthValid thirdAuthValid = AppService.getBean("thirdAuthValid");
    public static boolean checkSignValid(String sign, String ts, String params) {

        // ts + params
        String signStr = ts.concat(params);

        // 计算签名
        signStr = DigestUtils.md5Hex(signStr.getBytes());

        // 返回签名是否争取
        return signStr.equals(sign) ? true : false;
    }

    public static boolean checkFactoryAuth(String factoryid,String factorysecretkey){
        return thirdAuthValid.checkThirdPlatformAuth(factoryid, factorysecretkey);
    }


    public static void main(String[] args) {

        String sign = "bf7f4def171bcdced690b6a762e41787";
        String ts = "1528267773";
        String params = "{'hospac': 'a44030518042501', 'hospitalpwd': 'WcmRxk', 'hospcc': 'c44030518042301', 'hospitalacc': 'a44030518042501', 'logininfo': {'devmode': 'IPhone7plus', 'osname': 'iPhone9,2:10.2.1', 'ostype': '30000', 'appver': '1.09', 'oslang': 'zh', 'appid': 'G2DofqwTQCWclvn8x', 'imei': 'G2DofqwTQCWclvn8x'}}";
        //String params = "{ \"userid\":\"\",\"checkcode\":\"10001\",\"acc\":\"18600006666\",\"pwd\":\"VnsU3m+RrSIlufaa0/LnKfw25uh5mI5FCqsYB2sSyEvtV7j9zVgqzM1rsGu+1VS/8+8XTCzWdv9fHGt7KHWUph8srrNdOHbM6MmSneugYi5dFujOZrv3ZjGUh+OOG5AJS2KGga9JG2DofqwTQCWclvn8xCgyOadAF2bVw9QE0LY=\"}";
        boolean ret = CheckSign.checkSignValid(sign, ts, params);


        System.out.println(ret);
    }
}
