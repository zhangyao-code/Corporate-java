package com.codeages.corporate.api.admin;


import com.codeages.corporate.biz.user.dto.LoginParams;
import com.codeages.corporate.biz.user.dto.UserAuthedDto;
import com.codeages.corporate.biz.user.service.UserAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-admin/public")
public class AdminPublicController {

    private final UserAuthService authService;

    public AdminPublicController(UserAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public UserAuthedDto login(@RequestBody LoginParams params) {
        return authService.login(params);
    }

}
