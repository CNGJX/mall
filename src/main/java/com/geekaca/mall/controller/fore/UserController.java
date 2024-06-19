package com.geekaca.mall.controller.fore;

import cn.hutool.crypto.digest.DigestUtil;
import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.param.UserParam;
import com.geekaca.mall.domain.User;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.HomePageService;
import com.geekaca.mall.service.UserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HomePageService homePageService;
    /**
     * 参数  ：loginName,password ,password2
     * @return
     */
    @PostMapping("/user/register")
    public Result userReg(@RequestBody UserParam userParam){
        log.info("userParam:{}",userParam);
        String md5Pass = DigestUtil.md5Hex(userParam.getPasswordMd5());
        userParam.setPasswordMd5(md5Pass);
        boolean isRegisterOk = userService.register(userParam.getLoginName(), userParam.getPasswordMd5());


        if (isRegisterOk){
            return ResultGenerator.genSuccessResult("注册成功");
        }else{
            return ResultGenerator.genFailResult("注册失败");
        }
    }

    /**
     * 登陆成功后
     * 给前端返回token
     * @param userParam
     * @return
     */
    @PostMapping("/user/login")
    public Result userLogin(@RequestBody UserParam userParam){
        User user = userService.login(userParam);
        Result result = ResultGenerator.genSuccessResult();
        if (user != null){
            String token = JwtUtil.createToken(String.valueOf(user.getUserId()), user.getLoginName());
            result.setData(token);
            return result;
        }
        return ResultGenerator.genFailResult("登陆失败");
    }

    @GetMapping("/user/info")
    public Result getUserInfo(HttpServletRequest req) {
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
        User user = userService.getUserInfo(uid);
        if (user != null){
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genFailResult("获取用户信息失败");
    }

    @PutMapping("/user/info")
    public Result changeUserInfo(@RequestBody User user, HttpServletRequest req) {
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
        user.setUserId(uid);
        User userInfo = userService.getUserInfo(uid);
        if (user.getPasswordMd5() == null){ //说明不需要修改密码
            user.setPasswordMd5(userInfo.getPasswordMd5());
        }
        if (userInfo.getIntroduceSign().equals(user.getIntroduceSign()) &
                userInfo.getNickName().equals(user.getNickName()) &
                userInfo.getPasswordMd5().equals(user.getPasswordMd5())) {
            return ResultGenerator.genFailResult("新信息与原有信息一致，不需要更改");
        }
        int changed = userService.changeUserInfo(user);
        if (changed > 0) {
            return ResultGenerator.genSuccessResult("更新成功");
        }
        return ResultGenerator.genFailResult("更新失败");
    }

    @PostMapping("/user/logout")
    public Result userLogout(HttpServletRequest req) {
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
        if (uid != null){
            return ResultGenerator.genSuccessResult("退出成功");
        }
        return ResultGenerator.genFailResult("退出失败");
    }
}
