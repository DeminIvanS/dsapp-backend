package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.SubscriptionPlan;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubscriptionPlanDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private SubscriptionPlan.BillingMode billingMode;
    private boolean isActive;
    private LocalDateTime createdAt;

    public SubscriptionPlanDto() {
    }


    public static SubscriptionPlanDto from(SubscriptionPlan plan){
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.id = plan.getId();
        dto.name = plan.getName();
        dto.price = plan.getPrice();
        dto.billingMode = plan.getBillingMode();
        dto.isActive = plan.isActive();
        dto.createdAt = plan.getCreatedAt();
        return dto;
    }

    public SubscriptionPlan toEntity(){
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(this.id);
        plan.setName(this.name);
        plan.setPrice(this.price);
        plan.setBillingMode(this.billingMode);
        plan.setActive(this.isActive);
        plan.setCreatedAt(this.createdAt);
        return plan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SubscriptionPlan.BillingMode getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(SubscriptionPlan.BillingMode billingMode) {
        this.billingMode = billingMode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
