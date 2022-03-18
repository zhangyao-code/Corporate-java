package com.codeages.javaskeletonproject.security;

import cn.hutool.core.util.StrUtil;
import com.codeages.javaskeletonproject.biz.user.service.UserAuthService;
import com.codeages.javaskeletonproject.exception.AppException;
import com.codeages.javaskeletonproject.exception.AppError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    private final UserAuthService userAuthService;

    private final ObjectMapper objectMapper;

    public AuthTokenFilter(UserAuthService userAuthService, ObjectMapper objectMapper) {
        this.userAuthService = userAuthService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseToken(request);
            log.info("token {}", token);
            if (token != null) {
                var userAuthedDto = userAuthService.auth(token);
                var authUser = new AuthUser(userAuthedDto);
                authUser.setIp(request.getRemoteAddr());
                log.info("auth user {}", authUser);
                var authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (AppException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(e.getStatus());
            response.getWriter().write(objectMapper.writeValueAsString(AppError.fromAppException(e, request)));
        }
    }

    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StrUtil.isNotEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
