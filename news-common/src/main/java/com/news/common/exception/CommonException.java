package com.news.common.exception;

import com.news.common.enums.RespCodeEnum;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private RespCodeEnum respCodeEnum;

    public CommonException(RespCodeEnum respCodeEnum) {
        super(respCodeEnum.getDesc());
        this.respCodeEnum = respCodeEnum;
    }

}
