package com.platform.aix.common;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Advance
 * @create: 2022-03-31 09:41
 * @since V1.0.0
 */
@Repository
public interface QueryMapper {
    List<String>  queryForList();
}
