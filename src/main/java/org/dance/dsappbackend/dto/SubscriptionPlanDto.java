package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.dance.dsappbackend.enums.BillingMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SubscriptionPlanDto (

    Long id,
    String name,
    BigDecimal price,
    BillingMode billingMode,
    boolean isActive,
    LocalDateTime createdAt
){}
