package com.codeages.corporate.biz.objectlog.repository;

import com.codeages.corporate.biz.objectlog.entity.ObjectLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ObjectLogRepository extends JpaRepository<ObjectLog, Long>, QuerydslPredicateExecutor<ObjectLog> {
}
