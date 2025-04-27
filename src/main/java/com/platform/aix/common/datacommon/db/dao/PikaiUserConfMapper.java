package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiUserConf;
import com.platform.aix.common.datacommon.db.domain.PikaiUserConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiUserConfMapper extends IMybatisMapper<PikaiUserConf,Integer,PikaiUserConfExample> {
    int countByExample(PikaiUserConfExample example);

    int deleteByExample(PikaiUserConfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PikaiUserConf record);

    int insertSelective(PikaiUserConf record);

    List<PikaiUserConf> selectByExample(PikaiUserConfExample example);

    PikaiUserConf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PikaiUserConf record, @Param("example") PikaiUserConfExample example);

    int updateByExample(@Param("record") PikaiUserConf record, @Param("example") PikaiUserConfExample example);

    int updateByPrimaryKeySelective(PikaiUserConf record);

    int updateByPrimaryKey(PikaiUserConf record);
}