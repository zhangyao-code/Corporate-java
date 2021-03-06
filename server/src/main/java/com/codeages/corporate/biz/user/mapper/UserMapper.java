package com.codeages.corporate.biz.user.mapper;

import com.codeages.corporate.biz.user.dto.UserCreateParams;
import com.codeages.corporate.biz.user.dto.UserDto;
import com.codeages.corporate.biz.user.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    // 这个注解的作用是对入参的字段做 NULL 值检查，如果入参的字段为NULL，则不赋值到目标对象
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    User toEntity(UserCreateParams params);
}
