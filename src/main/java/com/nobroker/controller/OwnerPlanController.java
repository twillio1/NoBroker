package com.nobroker.controller;

import com.nobroker.entity.OwnerPlan;
import com.nobroker.service.OwnerPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/owner-plans")
public class OwnerPlanController {

    @Autowired
    private OwnerPlanService ownerPlanService;

    //http://localhost:8085/api/owner-plans/subscribe/30/1
    @PostMapping("/subscribe/{numberOfDays}/{userId}")
    public ResponseEntity<String> subscribe(
            @PathVariable int numberOfDays,
            @PathVariable long userId) {

        ownerPlanService.createSubscription(userId, numberOfDays);
        return new ResponseEntity<>("Subscription created for " + numberOfDays + " days", HttpStatus.CREATED);
    }

   // http://localhost:8085/api/owner-plans/1
    @GetMapping("/{ownerPlanId}")
    public ResponseEntity<?> getOwnerPlan(@PathVariable long ownerPlanId) {
        Optional<OwnerPlan> ownerPlanOptional = ownerPlanService.getOwnerPlanById(ownerPlanId);

        if (ownerPlanOptional.isPresent()) {
            return new ResponseEntity<>(ownerPlanOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("OwnerPlan not found", HttpStatus.NOT_FOUND);
        }
    }
}
