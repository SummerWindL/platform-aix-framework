package com.platform.aix.common.datacommon.db.dao;

import java.util.List;
import java.util.Optional;

import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.domain.PikaiUserConf;
import com.platform.aix.common.datacommon.db.domain.PikaiUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiUserMapper extends IMybatisMapper<PikaiUser,String, PikaiUserExample>{
    int countByExample(PikaiUserExample example);

    int deleteByExample(PikaiUserExample example);

    int deleteByPrimaryKey(String userId);

    int insert(PikaiUser record);

    int insertSelective(PikaiUser record);

    List<PikaiUser> selectByExample(PikaiUserExample example);

    PikaiUser selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") PikaiUser record, @Param("example") PikaiUserExample example);

    int updateByExample(@Param("record") PikaiUser record, @Param("example") PikaiUserExample example);

    int updateByPrimaryKeySelective(PikaiUser record);

    int updateByPrimaryKey(PikaiUser record);

    /**
     * 根据email查询账号
     * @author fuyanliang
     * @date 2025/4/17 17:54
     * @param email
     * @return com.platform.aix.common.datacommon.db.domain.PikaiUser
     */
    Optional<PikaiUser> findByAccountId(String email);

    /**
     * 判断当前账号是否存在
     * @author fuyanliang
     * @date 2025/4/17 17:57
     * @param email
     * @return boolean
     */
    boolean existsByAccountId(String email);

    /**
     * 插入返回实体
     * @author fuyanliang
     * @date 2025/4/17 18:10
     * @param record
     * @return com.platform.aix.common.datacommon.db.domain.PikaiUser
     */
    PikaiUser insertReturnEntity(PikaiUser record);

    /**
     * 更新插入记录
     * @author fuyanliang
     * @date 2025/5/29 17:43
     * @param record
     * @return int
     */
    int updateOrInsertSelective(PikaiUser record);
}