package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.*;
import org.dance.dsappbackend.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Пользователи", description = "Управление пользователями школы")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать ученика", description = "создает ученика, доступно только для ROLE_ADMIN")
    public CreatedUserDto createStudents(@RequestBody CreateStudentDto dto) {
        return userService.createStudent(dto);
    }

    @PostMapping("/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать преподавателя", description = "создает преподавателя, доступно только для ROLE_ADMIN")
    public CreatedUserDto createTeachers(@RequestBody CreateTeacherDto dto) {
        return userService.createTeacher(dto);
    }

    @PostMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Сбросить пароль юзера", description = "сбрасывает пароль, доступно только для ROLE_ADMIN")
    public ResetPasswordResponse resetPassword(@PathVariable Long id) {
        return userService.resetPassword(id);
    }
}
