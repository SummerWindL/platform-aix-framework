package com.platform.aix.service.processor;

import com.platform.aix.common.datacommon.db.dao.UserMapper;
import com.platform.aix.common.datacommon.db.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 医院电子档案
 * @author Advance
 * @date 2022年05月18日 15:23
 * @since V1.0.0
 */
@Component
@Slf4j
public class HospitalElectronicPaperProcessor {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 建档
     * @param users
     */
    public void createUserElectonicPaper(List<User> users){
        //模拟写入数据库
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        AtomicInteger index = new AtomicInteger(0);
        users.forEach(user -> {
            index.getAndIncrement();
            mapper.insertSelective(user);
            if(index.get() % 500  == 0){
                sqlSession.flushStatements();
            }
        });
        sqlSession.commit();
        sqlSession.close();
        log.info("写入数据库成功，批量写入用户信息成功");
    }
}
