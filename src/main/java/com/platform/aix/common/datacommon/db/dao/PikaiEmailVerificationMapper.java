package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification;
import com.platform.aix.common.datacommon.db.domain.PikaiEmailVerificationExample;
import java.util.List;
import java.util.Optional;

import com.platform.aix.common.datacommon.db.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiEmailVerificationMapper extends IMybatisMapper<PikaiEmailVerification,String, PikaiEmailVerificationExample> {
    int countByExample(PikaiEmailVerificationExample example);

    int deleteByExample(PikaiEmailVerificationExample example);

    int deleteByPrimaryKey(String verificationId);

    int insert(PikaiEmailVerification record);

    int insertSelective(PikaiEmailVerification record);

    List<PikaiEmailVerification> selectByExample(PikaiEmailVerificationExample example);

    PikaiEmailVerification selectByPrimaryKey(String verificationId);

    int updateByExampleSelective(@Param("record") PikaiEmailVerification record, @Param("example") PikaiEmailVerificationExample example);

    int updateByExample(@Param("record") PikaiEmailVerification record, @Param("example") PikaiEmailVerificationExample example);

    int updateByPrimaryKeySelective(PikaiEmailVerification record);

    int updateByPrimaryKey(PikaiEmailVerification record);

    List<PikaiEmailVerification> findByUserIdAndStatusOrderByCreateTimeDesc(String userId,String status);

    Optional<PikaiEmailVerification> findByVerificationCode(String verificationCode);

    int updateOrInsertSelective(PikaiEmailVerification record);
}