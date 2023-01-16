package com.news.user.controller;

import com.news.common.vo.RespVo;
import com.news.user.service.UserService;
import com.news.user.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public RespVo login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    @GetMapping("/user/{id}")
    public RespVo getUserInfo(@PathVariable Long id) {
        return userService.findUserInfoById(id);
    }
}
