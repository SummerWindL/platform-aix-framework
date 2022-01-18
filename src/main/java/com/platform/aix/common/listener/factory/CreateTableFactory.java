package com.platform.aix.common.listener.factory;

import com.platform.common.util.AssertUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * 建表工厂类
 * @author Advance
 * @date 2022年01月17日 15:37
 * @since V1.0.0
 */
public class CreateTableFactory {

    /**
     * 根据传递的驱动类型创建不同的对象
     * @author Advance
     * @date 2022/1/17 16:04
     * @param driveType
     * @return com.platform.aix.common.listener.factory.CreateTable
     */
     public static CreateTable getTableCreator(String driveType){
        AssertUtil.notNull(driveType, "驱动类型为空！");
        if(driveType.contains("Oracle")){
            return new CreateOracleTable();
        } else if(driveType.contains("PostgreSQL")){
            return new CreatePostgreSQLTable();
        }
        return new CreatePostgreSQLTable(); //默认返回postgresql建表对象
    }
}
