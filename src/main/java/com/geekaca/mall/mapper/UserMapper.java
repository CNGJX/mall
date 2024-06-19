package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.controller.param.UserParam;
import com.geekaca.mall.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author gjx
* @description 针对表【tb_newbee_mall_user】的数据库操作Mapper
* @createDate 2024-01-12 17:29:45
* @Entity com.geekaca.mall.domain.User
*/
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    // 添加用户
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据查询用户名返回条数判断是否已存在，大于0表示用户名已存在
     * @param loginName
     * @return
     */
    int selectCountByLoginName(String loginName);

    User selectByLoginNamePasswd(UserParam userParam);

    List<User> selectAllMember(PageParam pageParams);

    Integer selectAllRecord();

    int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);

}
