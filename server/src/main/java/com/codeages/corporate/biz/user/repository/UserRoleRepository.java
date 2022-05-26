package com.codeages.corporate.biz.user.repository;

import com.codeages.corporate.biz.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {

    List<UserRole> findAllByUserId(Long userId);
}
