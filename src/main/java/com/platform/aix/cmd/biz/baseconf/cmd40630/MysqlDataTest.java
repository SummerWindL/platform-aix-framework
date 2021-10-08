package com.platform.aix.cmd.biz.baseconf.cmd40630;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Advance
 * @date 2021年10月08日 18:32
 * @since V1.0.0
 */
@DS("slave")
@Component
public class MysqlDataTest {

    private JdbcTemplate jdbcTemplate;
    public MysqlDataTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List selectAll() {
        return  jdbcTemplate.queryForList("select * from user");
    }
}
