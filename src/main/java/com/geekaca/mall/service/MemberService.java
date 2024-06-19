package com.geekaca.mall.service;

import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.utils.PageResult;

public interface MemberService {
    PageResult selectAllMember(PageParam pageParams);
}
