package org.qilin.service.impl;

import org.qilin.constants.SystemConstants;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.LoginUser;
import org.qilin.domain.entity.User;
import org.qilin.domain.vo.BlogUserLoginVo;
import org.qilin.domain.vo.UserInfoVo;
import org.qilin.service.BlogLoginService;
import org.qilin.utils.BeanCopyUtils;
import org.qilin.utils.JwtUtil;
import org.qilin.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否验证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN + userId, loginUser);
        // 封装token 和 userInfo 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取loginUser
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userId
        Long userId = loginUser.getUser().getId();
        // redis删除对应key
        redisCache.deleteObject(SystemConstants.BLOG_LOGIN + userId);

        return ResponseResult.okResult();
    }
}
