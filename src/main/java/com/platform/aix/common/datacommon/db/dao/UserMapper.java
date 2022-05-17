package com.platform.aix.common.datacommon.db.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.domain.UserExample;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Advance
 * @create: 2022-03-26 11:00
 * @since V1.0.0
 */
@Repository("userMapper")
public interface UserMapper extends IMybatisMapper<User, String, UserExample> {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insert(User record);

    int insertSelective(User record);

    /**
     * Async最终调用 插入更新方法
     * @author Advance
     * @date 2022/3/26 12:09
     * @param record
     * @return int
     */
    @DS("slave_1")
    int updateOrInsertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
}
