package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.IndexConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author w
* @description 针对表【tb_newbee_mall_index_config】的数据库操作Mapper
* @createDate 2024-02-03 21:17:19
* @Entity com.geekaca.mall.domain.IndexConfig
*/
@Mapper
public interface IndexConfigMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    List<IndexConfig> getConfigsGoods(@Param("configType") Integer configType,
                                      @Param("start") Integer start,
                                      @Param("pageSize") Integer pageSize);

    int getConfigsGoodsCount(Integer configType);

    int deleteConfigs(Long[] ids);
}
