package com.news.common.exception;


import com.news.common.enums.RespCodeEnum;
import com.news.common.vo.RespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(-9999)
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(CommonException.class)
    public RespVo exception(CommonException exception) {
        log.error("fetch global common exception: {}", exception);
        return RespVo.error(exception.getRespCodeEnum());
    }

    @ExceptionHandler(Exception.class)
    public RespVo exception(Exception exception) {
        log.error("fetch global exception: {}", exception);
        return RespVo.error(RespCodeEnum.FAIL);
    }
}
