package com.news.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.news.common.enums.RespCodeEnum;
import com.news.common.vo.RespVo;
import com.news.user.common.KeyManager;
import com.news.user.entity.UserEntity;
import com.news.user.repository.UserRepository;
import com.news.user.service.UserService;
import com.news.user.vo.LoginVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private KeyManager keyManager;
    @Autowired
    private UserRepository userRepository;

    public RespVo login(LoginVo loginVo) {
        if (StrUtil.isBlank(loginVo.getPhone())) {
            return RespVo.error(RespCodeEnum.PHONE_NOT_BLANK);
        }
        if (StrUtil.isBlank(loginVo.getPassword())) {
            return RespVo.error(RespCodeEnum.PASSWORD_NOT_BLANK);
        }
        // 根据手机号从数据库中获取用户信息（手机号唯一）
        UserEntity queryUser = new UserEntity();
        queryUser.setPhone(loginVo.getPhone());
        List<UserEntity> users = userRepository.findAll(Example.of(queryUser));
        if (CollUtil.isEmpty(users)) {
            return RespVo.error(RespCodeEnum.ACCOUNT_NOT_EXIST);
        }
        UserEntity userEntity = users.get(0);
        if (!userEntity.getPassword().equals(SecureUtil.md5(loginVo.getPassword() + userEntity.getSalt()))) {
            return RespVo.error(RespCodeEnum.ACCOUNT_PASSWORD_NOT_ILLEGAL);
        }
        // 生成jwt token
        Map<String, Object> payloads = new HashMap<String, Object>();
        payloads.put("userId", userEntity.getId());
        String token = JWTUtil.createToken(payloads, keyManager.getJwtKey().getBytes());
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("token", token);
        info.put("username", userEntity.getUsername());
        return RespVo.ok(info);
    }

    @Override
    public RespVo findUserInfoById(Long id) {
        return RespVo.ok(userRepository.findById(id).orElse(null));
    }
}
