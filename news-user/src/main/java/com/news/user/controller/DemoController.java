package com.news.user.controller;

import com.news.common.enums.RespCodeEnum;
import com.news.common.exception.CommonException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoController {

    @GetMapping("/service")
    public String service() {
        return "User";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        throw new CommonException(RespCodeEnum.FAIL);
    }

}
