package com.codeages.corporate.biz.user.service;

import com.codeages.corporate.biz.user.dto.LoginParams;
import com.codeages.corporate.biz.user.dto.UserAuthedDto;

public interface UserAuthService {

    UserAuthedDto login(LoginParams params);

    /**
     * 认证 Token
     *
     * @param token
     * @return
     */
    UserAuthedDto auth(String token);
}
