package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.SubscriptionPlanDto;
import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {
    public SubscriptionPlanDto toSubscriptionPlanDto(SubscriptionPlan plan){
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setPrice(plan.getPrice());
        dto.setBillingMode(plan.getBillingMode());
        dto.setActive(plan.isActive());
        dto.setCreatedAt(plan.getCreatedAt());
        return dto;
    }

    public SubscriptionPlan toSubscriptionPlanEntity(SubscriptionPlanDto dto){
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(dto.getName());
        plan.setPrice(dto.getPrice());
        plan.setBillingMode(dto.getBillingMode());
        plan.setActive(dto.isActive());

        return plan;
    }
}
