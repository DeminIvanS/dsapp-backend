package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping
    public List<StudentDto> getAll() {
        return studentService.findAll();
    }

    @PostMapping
    public CreatedUserDto create(@RequestBody CreateStudentDto dto) {
        return studentService.createStudent(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody StudentDto dto) {
       studentService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}
