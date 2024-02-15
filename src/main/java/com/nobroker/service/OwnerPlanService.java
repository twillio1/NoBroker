package com.nobroker.service;

import com.nobroker.entity.OwnerPlan;
import com.nobroker.repository.OwnerPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerPlanService {

    @Autowired
    private OwnerPlanRepository ownerPlanRepository;

    public void createSubscription(long userId, int numberOfDays) {
        OwnerPlan ownerPlan = new OwnerPlan();
        ownerPlan.setUserId(userId);
        ownerPlan.setSubscriptonActive(true);
        ownerPlan.setSubscriptionActiveDate(LocalDate.now());
        ownerPlan.setNumberOfDays(numberOfDays);
        ownerPlan.setSubscriptionExpiryDate(LocalDate.now().plusDays(numberOfDays));

        ownerPlanRepository.save(ownerPlan);
    }

    public Optional<OwnerPlan> getOwnerPlanById(long ownerPlanId) {
        return ownerPlanRepository.findById(ownerPlanId);
    }

    public List<OwnerPlan> getExpiredOwnerPlans() {
        LocalDate currentDate = LocalDate.now();
        return ownerPlanRepository.findBySubscriptionExpiryDateBeforeAndSubscriptonActiveIsTrue(currentDate);
    }
}
