package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public TeacherDto getById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @GetMapping
    public List<TeacherDto> getAll() {
        return teacherService.findAll();
    }

    @PostMapping
    public CreatedUserDto create(@RequestBody CreateTeacherDto dto) {
        return teacherService.createTeacher(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody TeacherDto dto) {
       teacherService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}
