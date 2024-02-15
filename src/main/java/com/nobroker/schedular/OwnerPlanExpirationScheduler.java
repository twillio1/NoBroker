package com.nobroker.schedular;

import com.nobroker.entity.OwnerPlan;
import com.nobroker.service.OwnerPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerPlanExpirationScheduler {

    @Autowired
    private OwnerPlanService ownerPlanService;

    @Scheduled(cron = "0 0 0 * * ?")  // Run at midnight every day
    public void expireOwnerPlans() {
        List<OwnerPlan> activeOwnerPlans = ownerPlanService.getExpiredOwnerPlans();

        for (OwnerPlan ownerPlan : activeOwnerPlans) {
            ownerPlan.setSubscriptonActive(false);
            // Note: No need to explicitly save, as the changes are already managed by JPA
        }
    }
}
