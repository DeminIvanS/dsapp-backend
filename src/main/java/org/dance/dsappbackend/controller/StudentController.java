package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ученики", description = "Управление учениками школы")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ученика по id", description = "возвращает ученика по id, доступно для ROLE_ADMIN, ROLE_STUDENT и ROLE_TEACHER")
    public StudentDto getById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список учеников", description = "возвращает список учеников, доступно только для ROLE_ADMIN")
    public List<StudentDto> getAll() {
        return studentService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать ученика", description = "создает ученика, доступно только для ROLE_ADMIN")
    public CreatedUserDto create(@RequestBody CreateStudentDto dto) {
        return studentService.createStudent(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить ученика по id", description = "изменяет ученика по id, доступно для ROLE_ADMIN и ROLE_STUDENT")
    public void update(@PathVariable Long id, @RequestBody StudentDto dto) {
       studentService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ученика по id", description = "удаляет ученика по id, доступно только для ROLE_ADMIN")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}
