package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.param.AdminParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/manage-api/v1")
public class AdminHomeController {
    @Autowired
    private AdminUserService userService;


    @GetMapping("/adminUser/profile")
    @ApiOperation(value = "登录接口")
    public Result userLogin() {

        return ResultGenerator.genSuccessResult("获取成功");
    }

    @PutMapping("/adminUser/name")
    public Result changeLoginName(@RequestBody AdminUser user, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        // 把获取的id转换为字符串
        String sid = claim.asString();
        AdminUser adminUser = userService.getAdminUser(Long.valueOf(sid));
        if (adminUser == null){
            throw new MallException("获取AdminUser异常");
        }
        if (user.getLoginUserName().equals(adminUser.getLoginUserName())){
            return ResultGenerator.genFailResult("原用户名与新用户名一致");
        }
        if (user.getNickName().equals(adminUser.getNickName())){
            return ResultGenerator.genFailResult("原昵称与新昵称一致");
        }
        adminUser.setLoginUserName(user.getLoginUserName());
        adminUser.setNickName(user.getNickName());
        int changed = userService.changeUerLoginName(adminUser);
        if (changed == 0){
            return ResultGenerator.genFailResult("修改失败");
        }
        return ResultGenerator.genSuccessResult("修改成功");
    }

    @PutMapping("/adminUser/password")
    public Result changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("originalPassword") String originalPassword,
                                 HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null){
            throw new UserNotLoginException(CODE_USER_NOT_LOGIN, "用户未登录");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        // 把获取的id转换为字符串
        String sid = claim.asString();
        AdminUser adminUser = userService.getAdminUser(Long.valueOf(sid));
        if (adminUser == null){
            throw new MallException("获取AdminUser异常");
        }
        if (newPassword.equals(originalPassword)){
            return ResultGenerator.genFailResult("原密码与新密码一致");
        }
        if (!originalPassword.equals(adminUser.getLoginPassword())){
            return ResultGenerator.genFailResult("原密码输入错误");
        }
        adminUser.setLoginPassword(newPassword);
        int changed = userService.changeUerLoginName(adminUser);
        if (changed == 0){
            return ResultGenerator.genFailResult("修改失败");
        }
        return ResultGenerator.genSuccessResult("修改成功");
    }

    @PostMapping("/adminUser/login")
    public Result login(@Valid @RequestBody AdminParam adminParam) {
        String token = userService.login(adminParam);
        if (token == null){
            return ResultGenerator.genFailResult("登录失败");
        }else{
            Result rs = new Result();
            rs.setData(token);
            rs.setResultCode(ResultGenerator.RESULT_CODE_SUCCESS);
            return  rs;
        }
    }
}
