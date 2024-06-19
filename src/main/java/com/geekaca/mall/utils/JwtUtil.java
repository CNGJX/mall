package com.geekaca.mall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Jwt工具类，生成JWT和认证
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥
     * 密码本
     * 不要泄露出去
     */
    private static final String SECRET = "Geekaca826uois*^*^$^%####^(";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 60 * 60 * 24 * 30L;//单位为秒 1天

    /**
     * 生成用户token,设置token超时时间
     * 如果依赖的是MallAdminUser  ，依赖性太强， 可重用性收到限制
     * 降低耦合
     */
    public static String createToken(String uid, String uname) {
        //token 过期时间   1秒后过期
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //向token中加入自定义的信息
                // 可以将基本信息放到claims中
                .withClaim("id", uid)//userId
                .withClaim("userName", uname)//userName

                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);

//            jwt.getClaim("属性").asString() // 获取负载中的属性值

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("token解码异常");
        }

        return jwt.getClaims();
    }


}