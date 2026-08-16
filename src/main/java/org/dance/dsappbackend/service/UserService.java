package org.dance.dsappbackend.service;

import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public UserService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }
    public CreatedUserDto createStudent(CreateStudentDto dto){
        return studentService.createStudent(dto);
    }
    public CreatedUserDto createTeacher(CreateTeacherDto dto){
        return teacherService.createTeacher(dto);
    }
}
