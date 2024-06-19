package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.service.UserAddressService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.geekaca.mall.constants.MallConstants.CODE_USER_NOT_LOGIN;

@RestController
@RequestMapping("/api/v1")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/address")
    public Result getAddressList(HttpServletRequest req) {
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
        Integer uid = Integer.parseInt(sid);
        List<UserAddress> userAddressList = userAddressService.getUserAddressList(uid);
        if (userAddressList != null) {
            return ResultGenerator.genSuccessResult(userAddressList);
        } else {
            return ResultGenerator.genFailResult("地址获取异常");
        }
    }

    @GetMapping("/address/default")
    public Result getDefaultAddress(HttpServletRequest req) {
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
        Integer uid = Integer.parseInt(sid);
        UserAddress userDefaultAddr = userAddressService.getUserDefaultAddress(uid);
        return ResultGenerator.genSuccessResult(userDefaultAddr);

    }

    @PostMapping("/address")
    public Result addAddr(@RequestBody UserAddress userAddress, HttpServletRequest req) {
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
        Integer uid = Integer.parseInt(sid);
        userAddress.setUserId(Long.valueOf(uid));
        /**
         * 判断 userAddress是否设置为默认地址
         * 如果是，需要 把数据库之前的默认地址 取消
         */
        int added = userAddressService.addUserAddress(userAddress);
        if (added > 0) {
            return ResultGenerator.genSuccessResult("增加地址成功");
        } else {
            return ResultGenerator.genFailResult("增加地址失败");
        }
    }

    @PutMapping("/address")
    public Result updateUserAddress(@RequestBody UserAddress userAddress, HttpServletRequest req) {
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
        UserAddress userDefaultAddr = userAddressService.getUserDefaultAddress(uid.intValue());
        userAddress.setUserId(uid);
        int updated = userAddressService.updateUserAddress(userAddress);
        if (updated > 0) {
            if (userAddress.getDefaultFlag() == 1) {
                userDefaultAddr.setDefaultFlag(0);
                int updatedOld = userAddressService.updateUserAddress(userDefaultAddr);
                if (updatedOld > 0) {
                    return ResultGenerator.genSuccessResult("更新成功");
                }
            }
            return ResultGenerator.genSuccessResult("更新成功");
        }
        return ResultGenerator.genFailResult("更新失败");
    }

    @GetMapping("/address/{id}")
    public Result getOldAddress(@PathVariable("id") Integer id) {
        UserAddress userAddressOld = userAddressService.getUserAddressById(id);
        if (userAddressOld != null) {
            return ResultGenerator.genSuccessResult(userAddressOld);
        }
        return ResultGenerator.genFailResult("获取地址失败");
    }

    @DeleteMapping("/address/{id}")
    public Result deleteUserAddress(@PathVariable("id") Long id) {
        int deleted = userAddressService.deleteUserAddress(id);
        if (deleted > 0){
            return ResultGenerator.genSuccessResult("地址删除成功");
        }
        return ResultGenerator.genFailResult("地址删除失败");
    }
}
