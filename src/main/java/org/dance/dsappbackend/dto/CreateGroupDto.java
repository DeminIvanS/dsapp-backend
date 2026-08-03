package org.dance.dsappbackend.dto;

public record CreateGroupDto(
        Long branchId,
        Long teacherId,
        String name,
        String ageRange

)
{}
