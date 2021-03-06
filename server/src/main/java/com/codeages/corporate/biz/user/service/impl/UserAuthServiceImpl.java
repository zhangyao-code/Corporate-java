package com.codeages.corporate.biz.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codeages.corporate.biz.ErrorCode;
import com.codeages.corporate.biz.user.dto.LoginParams;
import com.codeages.corporate.biz.user.dto.UserAuthedDto;
import com.codeages.corporate.biz.user.entity.Role;
import com.codeages.corporate.biz.user.entity.User;
import com.codeages.corporate.biz.user.entity.UserRole;
import com.codeages.corporate.biz.user.repository.RoleRepository;
import com.codeages.corporate.biz.user.repository.UserRepository;
import com.codeages.corporate.biz.user.repository.UserRoleRepository;
import com.codeages.corporate.biz.user.service.UserAuthService;
import com.codeages.corporate.config.AppConfig;
import com.codeages.corporate.exception.AppException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final AppConfig config;

    private final UserRepository userRepo;

    private final UserRoleRepository userRoleRepo;

    private final RoleRepository roleRepo;

    private final PasswordEncoder encoder;

    public UserAuthServiceImpl(AppConfig config, UserRepository userRepo, UserRoleRepository userRoleRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.config = config;
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @Override
    public UserAuthedDto login(LoginParams params) {
        var user = userRepo.findByUsername(params.getUsername())
                .orElseThrow((() -> new AppException(ErrorCode.NOT_FOUND, "用户名或密码不正确")));

        if (!encoder.matches(params.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.NOT_FOUND, "用户名或密码不正确");
        }

        var algo = Algorithm.HMAC256(config.secret());
        var builder = JWT.create();
        builder.withExpiresAt(new Date(System.currentTimeMillis() + 86400000L*90L));
        builder.withSubject(user.getId().toString());
        var token = builder.sign(algo);

        return makeUserAuthedDto(user, token);
    }

    @Override
    public UserAuthedDto auth(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new AppException(ErrorCode.INVALID_AUTHENTICATION, "认证令牌解码失败");
        }

        try {
            JWT.require(Algorithm.HMAC256(config.secret()))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException(ErrorCode.INVALID_AUTHENTICATION, String.format("认证令牌校验失败(%s)", e.getMessage()));
        }

        Long userId;
        try {
            userId = Long.parseLong(jwt.getSubject());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_AUTHENTICATION, "认证令牌解码用户ID失败");
        }

        var user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_AUTHENTICATION, String.format("认证用户不存在(ID: %s)", userId)));

        return makeUserAuthedDto(user, token);
    }

    private UserAuthedDto makeUserAuthedDto(User user, String token) {
        var userRoles = userRoleRepo.findAllByUserId(user.getId());
        var userRoleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        var roles = roleRepo.findAllByIdIn(userRoleIds).stream().map(Role::getName).collect(Collectors.toList());

        var userAuthed = new UserAuthedDto();
        userAuthed.setId(user.getId());
        userAuthed.setUsername(user.getUsername());
        userAuthed.setRoles(roles);
        userAuthed.setToken(token);

        return userAuthed;
    }
}
