package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;

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
