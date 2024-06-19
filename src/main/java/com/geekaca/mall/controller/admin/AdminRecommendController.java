package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.HomePageService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/manage-api/v1")
public class AdminRecommendController {
    @Autowired
    private HomePageService homePageService;

    @GetMapping("/indexConfigs")
    public Result getAllConfigs(@RequestParam("pageNumber") Integer pageNumber,
                                @RequestParam("configType") Integer configType,
                                @RequestParam("pageSize") Integer pageSize) {
        PageResult result = homePageService.getConfigsGoods(pageNumber,pageSize,configType);
        return ResultGenerator.genSuccessResult(result);
    }

    @GetMapping("/indexConfigs/{id}")
    public Result getAllConfigs(@PathVariable("id") Long id) {
        IndexConfig configsGoods = homePageService.getGoodsOld(id);
        if (configsGoods == null){
            return ResultGenerator.genFailResult("获取首页商品数据失败");
        }
        return ResultGenerator.genSuccessResult(configsGoods);
    }

    @PostMapping("/indexConfigs")
    public Result addConfigs(@RequestBody IndexConfig indexConfig, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Long uid = Long.valueOf(sid);
        indexConfig.setCreateUser(uid.intValue());
        int added = homePageService.addConfigs(indexConfig);
        if (added > 0){
            return ResultGenerator.genSuccessResult("保存成功");
        }
        return ResultGenerator.genFailResult("保存失败");
    }

    @PutMapping("/indexConfigs")
    public Result updateConfigs(@RequestBody IndexConfig indexConfig, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String sid = claim.asString();
        if (sid == null) {
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Long uid = Long.valueOf(sid);
        indexConfig.setUpdateUser(uid.intValue());
        int updated = homePageService.updateConfigs(indexConfig);
        if (updated > 0){
            return ResultGenerator.genSuccessResult("更新成功");
        }
        return ResultGenerator.genFailResult("更新失败");
    }

    @DeleteMapping("/indexConfigs")
    public Result deleteConfigs(@RequestBody Map<String,Long[]> map) {
        Long[] ids = map.get("ids");
        int deleted = homePageService.deleteConfigs(ids);
        if (deleted > 0){
            return ResultGenerator.genSuccessResult("删除成功");
        }
        return ResultGenerator.genFailResult("删除失败");
    }
}
