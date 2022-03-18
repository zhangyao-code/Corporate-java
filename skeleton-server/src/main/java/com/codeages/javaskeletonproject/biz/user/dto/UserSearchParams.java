package com.codeages.javaskeletonproject.biz.user.dto;

import lombok.Data;

@Data
public class UserSearchParams {

    private String username;

    private String email;

    private Integer offset = 0;

    private Integer limit = 20;

}
