package com.platform.aix.common.datacommon.db.service;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.Prescription;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月24日 15:53
 */
public interface TCMService extends AsyncService<Integer, Prescription> {

    /**
     * 文件上传
     * @author fuyanliang
     * @date 2025/3/24 16:01
     * @param files
     */
    String storeFile(MultipartFile files);

    /**
     * 获取中医药方列表
     * @author fuyanliang
     * @date 2025/3/24 16:04
     * @return java.util.List<com.platform.aix.common.datacommon.db.domain.Prescription>
     */
    List<Prescription> getAllPrescriptions();

    /**
     * 保存中医药方
     * @author fuyanliang
     * @date 2025/3/24 16:05
     * @param prescription
     * @return com.platform.aix.common.datacommon.db.domain.Prescription
     */
    Prescription savePrescription(Prescription prescription);

    /**
     * 更新中医药方
     * @author fuyanliang
     * @date 2025/3/24 16:06
     * @param id
     * @param prescription
     * @return null
     */
    Prescription updatePrescription(Long id,Prescription prescription);

    /**
     * 删除中医药方
     * @author fuyanliang
     * @date 2025/3/24 16:08
     * @param ids
     */
    void deletePrescriptions(List<Long> ids);

    /**
     * 中医药方文件下载
     * @author fuyanliang
     * @date 2025/3/24 16:09
     * @param fileName
     * @return org.springframework.core.io.Resource
     */
    Resource loadFile(String fileName);

}
