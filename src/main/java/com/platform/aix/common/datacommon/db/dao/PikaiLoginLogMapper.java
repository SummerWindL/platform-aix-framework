package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiLoginLog;
import com.platform.aix.common.datacommon.db.domain.PikaiLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiLoginLogMapper extends IMybatisMapper<PikaiLoginLog,String, PikaiLoginLogExample> {
    int countByExample(PikaiLoginLogExample example);

    int deleteByExample(PikaiLoginLogExample example);

    int deleteByPrimaryKey(String loginId);

    int insert(PikaiLoginLog record);

    int insertSelective(PikaiLoginLog record);

    List<PikaiLoginLog> selectByExample(PikaiLoginLogExample example);

    PikaiLoginLog selectByPrimaryKey(String loginId);

    int updateByExampleSelective(@Param("record") PikaiLoginLog record, @Param("example") PikaiLoginLogExample example);

    int updateByExample(@Param("record") PikaiLoginLog record, @Param("example") PikaiLoginLogExample example);

    int updateByPrimaryKeySelective(PikaiLoginLog record);

    int updateByPrimaryKey(PikaiLoginLog record);
}