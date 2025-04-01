package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.Prescription;
import com.platform.aix.common.datacommon.db.domain.PrescriptionExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMapper extends IMybatisMapper<Prescription, Integer, PrescriptionExample>{
    int countByExample(PrescriptionExample example);

    int deleteByExample(PrescriptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Prescription record);

    Prescription insertReturnEntity(Prescription record);

    int insertSelective(Prescription record);

    List<Prescription> selectByExample(PrescriptionExample example);

    Prescription selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Prescription record, @Param("example") PrescriptionExample example);

    int updateByExample(@Param("record") Prescription record, @Param("example") PrescriptionExample example);

    int updateByPrimaryKeySelective(Prescription record);

    int updateByPrimaryKey(Prescription record);
}