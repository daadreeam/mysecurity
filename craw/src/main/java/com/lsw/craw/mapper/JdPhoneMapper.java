package com.lsw.craw.mapper;

import com.lsw.craw.entity.JdPhone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JdPhoneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(JdPhone record);

    int insertSelective(JdPhone record);

    JdPhone selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JdPhone record);

    int updateByPrimaryKey(JdPhone record);

    int updateBatch(List<JdPhone> list);

    int updateBatchSelective(List<JdPhone> list);

    int batchInsert(@Param("list") List<JdPhone> list);

    JdPhone selectBySkuId(Long skuId);
}
