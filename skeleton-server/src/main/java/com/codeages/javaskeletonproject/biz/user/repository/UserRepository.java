package com.codeages.javaskeletonproject.biz.user.repository;

import com.codeages.javaskeletonproject.biz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);
}
