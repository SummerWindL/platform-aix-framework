package com.platform.aix.common.datacommon.db.service.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PrescriptionMapper;
import com.platform.aix.common.datacommon.db.domain.Prescription;
import com.platform.aix.common.datacommon.db.service.TCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月24日 15:54
 */
@Service
public class TCMServiceImpl extends AsyncServiceImpl<Integer, Prescription> implements TCMService {
    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Override
    public IMybatisMapper<Prescription, Integer, ?> getMapper() {
        return prescriptionMapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(Prescription prescription) {

    }

    @Override
    protected void save(Prescription prescription) {

    }

    @Override
    protected void delete(Integer PK) {

    }

    @Override
    public String storeFile(MultipartFile files) {

        return "";
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionMapper.selectByExample(null);
    }

    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionMapper.insertReturnEntity(prescription);
    }

    @Override
    public Prescription updatePrescription(Long id, Prescription prescription) {
        return null;
    }

    @Override
    public void deletePrescriptions(List<Long> ids) {

    }

    @Override
    public Resource loadFile(String fileName) {
        return null;
    }
}
