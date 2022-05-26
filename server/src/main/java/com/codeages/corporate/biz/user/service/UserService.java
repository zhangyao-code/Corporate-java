package com.codeages.corporate.biz.user.service;

import com.codeages.corporate.biz.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserDto create(UserCreateParams params);

    void lock(Long id);

    void unlock(Long id);

    Optional<UserDto> get(Long id);

    Page<UserDto> search(UserSearchParams params, Pageable pager);
}
