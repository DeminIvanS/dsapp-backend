package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Создать ученика", description = "создает ученика, доступно только для ROLE_ADMIN")
    public CreatedUserDto createStudents(@RequestBody CreateStudentDto dto) {
        return userService.createStudent(dto);
    }

    @PostMapping("/teachers")
    @Operation(summary = "Создать преподавателя", description = "создает преподавателя, доступно только для ROLE_ADMIN")
    public CreatedUserDto createTeachers(@RequestBody CreateTeacherDto dto) {
        return userService.createTeacher(dto);
    }
}
