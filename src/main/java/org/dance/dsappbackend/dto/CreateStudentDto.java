package org.dance.dsappbackend.dto;

import java.time.LocalDate;

public record CreateStudentDto(
        String username,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthdate,
        String parentName,
        String phone,
        String referralSource
) {}
