package com.codeages.javaskeletonserver.biz.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class UserCreateParams {
    @NotEmpty
    @Length(min = 2, max = 64)
    private String username;

    @NotEmpty
    @Length(min = 5, max = 64)
    private String password;
}
