package com.news.user.interceptor;

import cn.hutool.core.util.StrUtil;
import com.news.common.common.UserContext;
import com.news.common.common.UserContextHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户上下
 */
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/download")) {
            return true;
        }
        String userId = request.getHeader("userId");
        if (StrUtil.isBlank(userId)) {
            return false;
        }
        UserContext userContext = new UserContext();
        userContext.setUserId(Long.valueOf(userId));
        UserContextHelper.set(userContext);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContextHelper.remove();
    }
}
