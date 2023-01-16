package com.news.user.service;

import com.news.common.vo.RespVo;
import com.news.user.vo.LoginVo;

public interface UserService {
    /**
     * 登陆
     *
     * @param loginVo 登陆请求
     * @return 登陆成功后的token信息及用户信息
     */
    RespVo login(LoginVo loginVo);

    RespVo findUserInfoById(Long id);
}
