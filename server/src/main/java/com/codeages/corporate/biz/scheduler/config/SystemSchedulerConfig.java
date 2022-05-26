package com.codeages.corporate.biz.scheduler.config;

import com.codeages.corporate.biz.scheduler.service.CronJobDeclare;
import com.codeages.corporate.biz.scheduler.service.IntervalJobDeclare;

import java.util.ArrayList;
import java.util.List;

public interface SystemSchedulerConfig {

    default List<IntervalJobDeclare> declareIntervalJobs() {
        return new ArrayList<>();
    }

    default List<CronJobDeclare> declareCronJobs() {
        return new ArrayList<>();
    }
}
