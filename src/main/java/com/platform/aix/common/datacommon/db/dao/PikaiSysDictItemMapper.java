//package com.platform.aix.common.datacommon.db.dao;
//
//import com.platform.aix.common.datacommon.db.domain.PikaiSysDictItem;
//import com.platform.aix.common.datacommon.db.domain.PikaiSysDictItemExample;
//import java.util.List;
//import org.apache.ibatis.annotations.Param;
//
//public interface PikaiSysDictItemMapper {
//    int countByExample(PikaiSysDictItemExample example);
//
//    int deleteByExample(PikaiSysDictItemExample example);
//
//    int deleteByPrimaryKey(Long id);
//
//    int insert(PikaiSysDictItem record);
//
//    int insertSelective(PikaiSysDictItem record);
//
//    List<PikaiSysDictItem> selectByExample(PikaiSysDictItemExample example);
//
//    PikaiSysDictItem selectByPrimaryKey(Long id);
//
//    int updateByExampleSelective(@Param("record") PikaiSysDictItem record, @Param("example") PikaiSysDictItemExample example);
//
//    int updateByExample(@Param("record") PikaiSysDictItem record, @Param("example") PikaiSysDictItemExample example);
//
//    int updateByPrimaryKeySelective(PikaiSysDictItem record);
//
//    int updateByPrimaryKey(PikaiSysDictItem record);
//}