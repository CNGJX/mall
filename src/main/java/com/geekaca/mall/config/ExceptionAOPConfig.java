package com.geekaca.mall.config;

import com.geekaca.mall.constants.MallConstants;
import com.geekaca.mall.exceptions.LoginNameExsistsException;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.exceptions.UserNotLoginException;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAOPConfig {
    /**
     * 配置 拦截哪种异常
     * @param exception
     * @return 拦截到指定种类的异常后，对应的controller接口返回结构
     */
    @ExceptionHandler(value = LoginNameExsistsException.class)
    public Result handleUserEx(LoginNameExsistsException exception){
        log.error("注册，用户名已经被占用", exception.getMessage());
        return ResultGenerator.genFailResult(exception.getMessage());
    }
    @ExceptionHandler(value = UserNotLoginException.class)
    public Result handleUserNotLoginEx(UserNotLoginException exception){
        log.error("用户未登录", exception.getMessage());
        Result rsf = new Result();
        rsf.setResultCode(MallConstants.CODE_USER_NOT_LOGIN);
        rsf.setMessage(exception.getMessage());
        return rsf;
    }

    @ExceptionHandler(value = MallException.class)
    public Result handleMallEx(MallException exception){
        log.error(" 业务异常", exception.getMessage());
        return ResultGenerator.genFailResult(exception.getMessage());
    }

    /**
     * 参数格式不符合要求，会触发MethodArgumentNotValidException 异常
     * 参数不匹配异常
     * 函数返回值：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("------->MethodArgumentNotValidException参数异常-------- ", e);
        //接口返回一个失败的结果  后面：获取 违反了domain类声明的哪个规则
        return ResultGenerator.genFailResult("参数异常" + e.getBindingResult().getFieldError().getDefaultMessage());
        //getDefaultMessage()会返回message信息
    }
}
