package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Преподаватели", description = "Управление преподавателями школы")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить преподавателя по id", description = "возвращает преподавателя по id, доступно для ROLE_ADMIN и ROLE_TEACHER")
    public TeacherDto getById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список преподавателей", description = "возвращает список преподавателей, доступно только для ROLE_ADMIN")
    public List<TeacherDto> getAll() {
        return teacherService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать преподавателя", description = "создает преподавателя, доступно только для ROLE_ADMIN")
    public CreatedUserDto create(@RequestBody CreateTeacherDto dto) {
        return teacherService.createTeacher(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить преподавателя по id", description = "изменяет преподавателя по id, доступно для ROLE_ADMIN и ROLE_TEACHER")
    public void update(@PathVariable Long id, @RequestBody TeacherDto dto) {
       teacherService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить преподавателя по id", description = "удаляет преподавателя по id, доступно только для ROLE_ADMIN")
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}
