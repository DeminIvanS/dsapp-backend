package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Hall;

import java.time.LocalDateTime;


public record HallDto(
        Long id,
        Long branchId,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
