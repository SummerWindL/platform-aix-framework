package com.platform.aix.service.user.inspection;

import com.platform.aix.service.user.inspection.bean.InspectionDatas;

import java.util.List;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-22 10:32
 **/

public interface UserInspectionDataService {
    /**
     * 批量插入检测数据
     * @param list
     */
    void batchInsertInspectionData(List<InspectionDatas> list);

}
