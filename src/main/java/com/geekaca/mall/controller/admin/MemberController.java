package com.geekaca.mall.controller.admin;
import com.geekaca.mall.controller.param.BatchIdParam;
import com.geekaca.mall.controller.param.PageParam;
import com.geekaca.mall.service.MemberService;
import com.geekaca.mall.service.UserService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    /**
     * 查询所有会员
     */
    @ApiOperation(value = "会员列表", notes = "查询所有会员")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize) {
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        // 用自己创建的类接受前端参数
        PageParam pageParams = new PageParam();
        pageParams.setPageNO(pageNumber);
        pageParams.setPageSize(pageSize);
        PageResult mallMemberInfos = memberService.selectAllMember(pageParams);
        return ResultGenerator.genSuccessResult(mallMemberInfos);
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/users/{lockStatus}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户状态", notes = "批量修改，用户禁用与解除禁用(0-未锁定 1-已锁定)")
    public Result lockUser(@RequestBody BatchIdParam batchIdParam, @PathVariable int lockStatus) {
        if (batchIdParam==null||batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (userService.lockUsers(batchIdParam.getIds(), lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }
}
