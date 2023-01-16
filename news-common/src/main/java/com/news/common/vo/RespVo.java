package com.news.common.vo;

import com.news.common.enums.RespCodeEnum;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class RespVo<T> {

    private int resultCode = RespCodeEnum.SUCCESS.getCode();
    private String message;
    private boolean isSuccess;
    private T data;

    public static RespVo ok(Object data) {
        RespVo respVo = new RespVo();
        respVo.setData(data);
        respVo.setMessage(RespCodeEnum.SUCCESS.getDesc());
        return respVo;
    }

    public static RespVo error(RespCodeEnum respCodeEnum) {
        RespVo respVo = new RespVo();
        respVo.setResultCode(respCodeEnum.getCode());
        respVo.setMessage(respCodeEnum.getDesc());
        return respVo;
    }

    public static RespVo page(Page page) {
        PageVo<Object> pageVo = PageVo.builder()
                .page(page.getPageable().getPageNumber())
                .pageSize(page.getPageable().getPageSize())
                .pages(page.getTotalPages())
                .total(page.getTotalElements())
                .content(page.getContent())
                .build();
        return ok(pageVo);
    }

    public boolean isSuccess() {
        return resultCode == RespCodeEnum.SUCCESS.getCode();
    }
}
