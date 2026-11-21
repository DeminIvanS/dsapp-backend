package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Group;
import org.dance.dsappbackend.entity.Teacher;

import java.time.LocalDateTime;


public record GroupDto (
        Long id,
        Long branchId,
        Long teacherId,
        String name,
        String ageRange,
        LocalDateTime createdAt
){
}
