package org.qilin.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.apache.poi.util.StringUtil;
import org.qilin.constants.SystemConstants;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.LoginUser;
import org.qilin.enums.AppHttpCodeEnum;
import org.qilin.utils.JwtUtil;
import org.qilin.utils.RedisCache;
import org.qilin.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        // 没有token 直接放行
        if (!Strings.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取userid
        Claims claims = null;
        try {
             claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            // 解析失败 说明 token不合法
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 从redis中获取用户信息
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.BLOG_LOGIN + userId);
        // 如果获取不到，说明登录过期了
        if (Objects.isNull(loginUser)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 把用户信息存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
