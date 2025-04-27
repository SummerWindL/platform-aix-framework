package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuthExample;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuthKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiThirdPartyAuthMapper {
    int countByExample(PikaiThirdPartyAuthExample example);

    int deleteByExample(PikaiThirdPartyAuthExample example);

    int deleteByPrimaryKey(PikaiThirdPartyAuthKey key);

    int insert(PikaiThirdPartyAuth record);

    int insertSelective(PikaiThirdPartyAuth record);

    List<PikaiThirdPartyAuth> selectByExample(PikaiThirdPartyAuthExample example);

    PikaiThirdPartyAuth selectByPrimaryKey(PikaiThirdPartyAuthKey key);

    int updateByExampleSelective(@Param("record") PikaiThirdPartyAuth record, @Param("example") PikaiThirdPartyAuthExample example);

    int updateByExample(@Param("record") PikaiThirdPartyAuth record, @Param("example") PikaiThirdPartyAuthExample example);

    int updateByPrimaryKeySelective(PikaiThirdPartyAuth record);

    int updateByPrimaryKey(PikaiThirdPartyAuth record);
}