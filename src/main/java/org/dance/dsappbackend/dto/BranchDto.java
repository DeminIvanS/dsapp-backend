package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import java.time.LocalDateTime;

public record BranchDto(
        Long id,
        String name,
        String address,
        LocalDateTime createdAt
) {
}
