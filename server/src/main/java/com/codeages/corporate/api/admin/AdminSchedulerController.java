package com.codeages.corporate.api.admin;

import com.codeages.corporate.biz.scheduler.service.SchedulerService;
import com.codeages.corporate.common.OkResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api-admin/scheduler")
public class AdminSchedulerController {

    private final SchedulerService schedulerService;

    public AdminSchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @RolesAllowed("ROLE_SUPER_ADMIN")
    @PostMapping("/resetSystemJobs")
    public OkResponse resetSystemJobs() {
        schedulerService.resetSystemJobs();
        return OkResponse.TRUE;
    }
}
