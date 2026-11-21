package org.dance.dsappbackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SubscriptionDto (
    Long id,
    Long studentId,
    Long priceListItemId,
    String subscriptionName,
    BigDecimal amount,
    LocalDateTime assignedAt
) {}

