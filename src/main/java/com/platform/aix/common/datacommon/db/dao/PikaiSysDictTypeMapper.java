//package com.platform.aix.common.datacommon.db.dao;
//
//import com.platform.aix.common.datacommon.db.domain.PikaiSysDictType;
//import com.platform.aix.common.datacommon.db.domain.PikaiSysDictTypeExample;
//import java.util.List;
//import org.apache.ibatis.annotations.Param;
//
//public interface PikaiSysDictTypeMapper {
//    int countByExample(PikaiSysDictTypeExample example);
//
//    int deleteByExample(PikaiSysDictTypeExample example);
//
//    int deleteByPrimaryKey(String dictCode);
//
//    int insert(PikaiSysDictType record);
//
//    int insertSelective(PikaiSysDictType record);
//
//    List<PikaiSysDictType> selectByExample(PikaiSysDictTypeExample example);
//
//    PikaiSysDictType selectByPrimaryKey(String dictCode);
//
//    int updateByExampleSelective(@Param("record") PikaiSysDictType record, @Param("example") PikaiSysDictTypeExample example);
//
//    int updateByExample(@Param("record") PikaiSysDictType record, @Param("example") PikaiSysDictTypeExample example);
//
//    int updateByPrimaryKeySelective(PikaiSysDictType record);
//
//    int updateByPrimaryKey(PikaiSysDictType record);
//}