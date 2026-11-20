package org.dance.dsappbackend.dto;


import java.time.LocalDateTime;


public record TeacherDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String patronymic,
        LocalDateTime createdAt) {
}
