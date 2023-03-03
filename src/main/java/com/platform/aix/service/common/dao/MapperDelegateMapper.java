/**
 * 
 */
package com.platform.aix.service.common.dao;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MapperDelegateMapper {

    @DeleteProvider(type = MapperDelegateSqlProvider.class, method = "deleteBySpecificKey")
    int deleteBySpecificKey(Map<String, Object> param);
}
