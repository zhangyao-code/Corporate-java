package com.codeages.javaskeletonproject.biz.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.codeages.javaskeletonproject.biz.ErrorCode;
import com.codeages.javaskeletonproject.biz.user.dto.UserCreateParams;
import com.codeages.javaskeletonproject.biz.user.dto.UserDto;
import com.codeages.javaskeletonproject.biz.user.dto.UserSearchParams;
import com.codeages.javaskeletonproject.biz.user.entity.QUser;
import com.codeages.javaskeletonproject.biz.user.mapper.UserMapper;
import com.codeages.javaskeletonproject.biz.user.repository.UserRepository;
import com.codeages.javaskeletonproject.biz.user.service.UserService;
import com.codeages.javaskeletonproject.exception.AppException;
import com.codeages.javaskeletonproject.security.SecurityContext;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    private final UserMapper mapper;

    private final Validator validator;

    private final PasswordEncoder encoder;

    private final SecurityContext context;

    public UserServiceImpl(UserRepository repo, UserMapper mapper, Validator validator, PasswordEncoder encoder, SecurityContext context) {
        this.repo = repo;
        this.mapper = mapper;
        this.validator = validator;
        this.encoder = encoder;
        this.context = context;
    }

    @Override
    public UserDto create(UserCreateParams params) {
        var errors = validator.validate(params);
        if (!errors.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_ARGUMENT, errors);
        }

        repo.findByUsername(params.getUsername()).ifPresent((u) -> {
            throw new AppException(ErrorCode.INVALID_ARGUMENT, "用户名已存在");
        });

        var user = mapper.toEntity(params);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRegisterAt(System.currentTimeMillis());
        user.setRegisterIp(context.getUser().getIp());

        repo.save(user);
        return mapper.toDto(user);
    }

    @Override
    public void lock(Long id) {
        var user = repo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "用户不存在"));
        user.setLocked(true);
        repo.save(user);
    }

    @Override
    public void unlock(Long id) {
        var user = repo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "用户不存在"));
        user.setLocked(false);
        repo.save(user);
    }

    @Override
    public Optional<UserDto> get(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Override
    public Page<UserDto> search(UserSearchParams params, Pageable pager) {
        var q = QUser.user;
        var builder = new BooleanBuilder();

        if (StrUtil.isNotEmpty(params.getUsername())) {
            builder.and(q.username.eq(params.getUsername()));
        }

        if (StrUtil.isNotEmpty(params.getEmail())) {
            builder.and(q.username.eq(params.getEmail()));
        }

        return repo.findAll(builder, pager).map(mapper::toDto);
    }
}
