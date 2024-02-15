package com.nobroker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="owner_plans")
@Data
public class OwnerPlan{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerPlanId;
    private long userId;
    private boolean subscriptonActive;
    private LocalDate subscriptionActiveDate;
    private LocalDate subscriptionExpiryDate;
    private int numberOfDays;
}
