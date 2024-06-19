package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.param.GoodsParam;
import com.geekaca.mall.controller.param.HomeGoodsInfo;
import com.geekaca.mall.domain.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author w
* @description 针对表【tb_newbee_mall_goods_info】的数据库操作Mapper
* @createDate 2024-01-10 11:11:02
* @Entity com.geekaca.mall.domain.GoodsInfo
*/
@Mapper
public interface GoodsInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    List<HomeGoodsInfo> getAllGoods(@Param("type") Integer type, @Param("limit") Integer limit);

    GoodsInfo getGoodsById(Long id);

    List<GoodsInfo> selectGoodsListByCondition(GoodsParam goodsParam);

    int selectGoodsCountByCondition(GoodsParam goodsParam);

    int goodsCountByPrice(@Param("keyword") String keyword,
                          @Param("goodsCategoryId") Integer goodsCategoryId);

    List<GoodsInfo> goodsListByKeyword(@Param("keyword") String keyword,
                                           @Param("orderBy") String orderBy,
                                           @Param("pageNum") Integer pageNumber,
                                           @Param("goodsCategoryId") Integer goodsCategoryId,
                                           @Param("limit") Integer limit);

    int goodsCountByKeyword(@Param("keyword") String keyword,
                            @Param("orderBy") String orderBy,
                            @Param("goodsCategoryId") Integer goodsCategoryId);
}
