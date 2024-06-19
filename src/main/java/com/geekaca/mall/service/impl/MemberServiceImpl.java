package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.domain.User;
import com.geekaca.mall.mapper.UserMapper;
import com.geekaca.mall.service.MemberService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    UserMapper mallMemberMapper;

    @Override
    public PageResult selectAllMember(PageParam pageParams) {
        //sql分页语句中limit的第一个参数，起始索引
        Integer startIndex = (pageParams.getPageNO() - 1) * pageParams.getPageSize();
        //设置起始索引属性
        pageParams.setStartIndex(startIndex);

        List<User> mallGoodsInfos = mallMemberMapper.selectAllMember(pageParams);

        Integer totalRecord = mallMemberMapper.selectAllRecord();

        PageResult pageResult = new PageResult(mallGoodsInfos, totalRecord, pageParams.getPageSize(), pageParams.getPageNO());


        return pageResult;
    }
}
