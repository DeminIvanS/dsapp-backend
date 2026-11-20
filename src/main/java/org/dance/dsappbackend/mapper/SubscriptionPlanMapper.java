package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.SubscriptionPlanDto;
import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {
    public SubscriptionPlanDto toSubscriptionPlanDto(SubscriptionPlan plan){
        return new SubscriptionPlanDto(
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getBillingMode(),
                plan.isActive(),
                plan.getCreatedAt());
    }

    public SubscriptionPlan toSubscriptionPlanEntity(SubscriptionPlanDto dto){
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(dto.name());
        plan.setPrice(dto.price());
        plan.setBillingMode(dto.billingMode());
        plan.setActive(dto.isActive());
        return plan;
    }
}
