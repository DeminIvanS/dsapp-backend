package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;

public record CreateTeacherDto(
        String username,
        String firstName,
        String lastName,
        String patronymic
)
{}
