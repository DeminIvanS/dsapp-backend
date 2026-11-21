package org.dance.dsappbackend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthdate,
        String parentName,
        String phone,
        String referralSource,
        LocalDateTime createdAt) {




}
