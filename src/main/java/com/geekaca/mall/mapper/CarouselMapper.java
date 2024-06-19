package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.domain.Carousel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author w
* @description 针对表【tb_newbee_mall_carousel】的数据库操作Mapper
* @createDate 2024-01-15 12:27:23
* @Entity com.geekaca.mall.domain.Carousel
*/
@Mapper
public interface CarouselMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);

    List<Carousel> getGoodsCarousel();

    List<Carousel> findCarouselList(PageParam pageParam);

    int getTotalCarousels();

    int deleteBatch(Long[] ids);
}
