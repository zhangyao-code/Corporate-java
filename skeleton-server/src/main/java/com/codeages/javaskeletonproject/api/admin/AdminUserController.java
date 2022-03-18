package com.codeages.javaskeletonproject.api.admin;

import com.codeages.javaskeletonproject.biz.ErrorCode;
import com.codeages.javaskeletonproject.biz.user.dto.UserCreateParams;
import com.codeages.javaskeletonproject.biz.user.dto.UserDto;
import com.codeages.javaskeletonproject.biz.user.dto.UserSearchParams;
import com.codeages.javaskeletonproject.biz.user.service.UserService;
import com.codeages.javaskeletonproject.common.IdPayload;
import com.codeages.javaskeletonproject.common.OkResponse;
import com.codeages.javaskeletonproject.common.PagerRequest;
import com.codeages.javaskeletonproject.common.PagerResponse;
import com.codeages.javaskeletonproject.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-admin/user")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDto create(@RequestBody UserCreateParams params) {
        return userService.create(params);
    }

    @PostMapping("/lock")
    public OkResponse lock(@RequestBody IdPayload id) {
        userService.lock(id.getId());
        return OkResponse.TRUE;
    }

    @PostMapping("/unlock")
    public OkResponse unlock(@RequestBody IdPayload id) {
        userService.unlock(id.getId());
        return OkResponse.TRUE;
    }

    @GetMapping("/get")
    public UserDto get(@RequestParam Long id) {
        return userService.get(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "用户不存在"));
    }

    @GetMapping("/search")
    public PagerResponse<UserDto> search(UserSearchParams params) {
        log.info("UserSearchParams {}", params);
        var pager = new PagerRequest(params.getOffset(), params.getLimit(), Sort.by("createdAt").descending());
        var users = userService.search(params, pager);
        return new PagerResponse<>(users, pager);
    }
}
