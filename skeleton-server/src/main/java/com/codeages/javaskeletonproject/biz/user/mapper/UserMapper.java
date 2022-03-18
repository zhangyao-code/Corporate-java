package com.codeages.javaskeletonproject.biz.user.mapper;

import com.codeages.javaskeletonproject.biz.user.dto.UserCreateParams;
import com.codeages.javaskeletonproject.biz.user.dto.UserDto;
import com.codeages.javaskeletonproject.biz.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);


    User toEntity(UserCreateParams params);

}
