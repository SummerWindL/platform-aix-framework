package com.platform.aix.common.request;

import com.platform.aix.service.base.AbstractServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @description: 第三方厂商校验
 * @author: fuyl
 * @create: 2020-07-29 14:49
 **/
@Component
public class ThirdAuthValid extends AbstractServiceImpl {
//    @Autowired
//    public FZxapigatewayAuthValidateQueryRepository fZxapigatewayAuthValidateQueryRepository;// = AppService.getBean("fZxapigatewayAuthValidateQueryRepository");

    public boolean checkThirdPlatformAuth(String factoryid, String factorysecretkey){
//        BasePlpgsqlModel basePlpgsqlModel = fZxapigatewayAuthValidateQueryRepository.fZxapigatewayAuthValidateQuery(factoryid);
//        TMbThirdFactoryRegist[] tMbThirdFactoryRegists = basePlpgsqlModel2Clz(basePlpgsqlModel, TMbThirdFactoryRegist[].class);
//        List<TMbThirdFactoryRegist> list = CollectionUtils.arrayToList(tMbThirdFactoryRegists);
//        if(!CollectionUtils.isEmpty(list)){
//            TMbThirdFactoryRegist tMbThirdFactoryRegist = list.get(0);
//            if(factoryid.equals(tMbThirdFactoryRegist.getFactoryid()) & factorysecretkey.equals(tMbThirdFactoryRegist.getFactorysecretkey())){
//                return true;
//            }
//        }
        return true;
    }

    @Data
    public static class TMbThirdFactoryRegist{
        /**
         * factoryid
         */
        private String factoryid;

        /**
         * factoryname
         */
        private String factoryname;

        /**
         * factorykeyid
         */
        private String factorykeyid;

        private String factorysecretkey;

        /**
         * expiredays
         */
        private Integer expiredays;

        /**
         * openflag
         */
        private Integer openflag;

        /**
         * hospcode
         */
        private String hospcode;

        /**
         * hospname
         */
        private String hospname;

        /**
         * reserveflag
         */
        private Integer reserveflag;


    }
}
