package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.dance.dsappbackend.enums.BillingMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubscriptionPlanDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private BillingMode billingMode;
    private boolean isActive;
    private LocalDateTime createdAt;

    public SubscriptionPlanDto() {
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

    public BillingMode getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(BillingMode billingMode) {
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
