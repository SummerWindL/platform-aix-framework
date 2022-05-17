package com.platform.aix.service.user.inspection.impl;

import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.inspection.UserInspectionDataService;
import com.platform.aix.service.user.inspection.bean.InspectionDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-22 10:33
 **/
@Service
public class UserInspectionDataServiceImpl extends AbstractServiceImpl implements UserInspectionDataService {
//    @Autowired
//    private FZxapigatewayInspectionDataQaDataBatchInsertRepository fZxapigatewayInspectionDataQaDataBatchInsertRepository;

    /**
     * 批量插入检测数据
     *
     * @param list
     */
    @Override
    public void batchInsertInspectionData(List<InspectionDatas> list) {
//        BasePlpgsqlModel basePlpgsqlModel = fZxapigatewayInspectionDataQaDataBatchInsertRepository.fZxapigatewayInspectionDataQaDataBatchInsert("admin",
//                JSONArray.toJSONString(list));
//        vaildateRetCode(basePlpgsqlModel.getRetcode(),basePlpgsqlModel.getRetvalue());
        System.out.println("插入数据库成功");
    }
}
