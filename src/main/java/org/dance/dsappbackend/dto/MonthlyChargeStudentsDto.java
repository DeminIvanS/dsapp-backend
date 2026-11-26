package org.dance.dsappbackend.dto;

import java.math.BigDecimal;

public record MonthlyChargeStudentsDto(
        Long studentId,
        Long groupId,
        Long subscriptionPlanId,
        BigDecimal price
) {
}
