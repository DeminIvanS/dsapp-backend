package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final UserRepository userRepository;
    private final TemporaryPasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;

    public UserService(StudentService studentService, TeacherService teacherService, PasswordEncoder passwordEncoder, UserRepository userRepository, TemporaryPasswordGenerator passwordGenerator) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
    }
    public CreatedUserDto createStudent(CreateStudentDto dto){
        return studentService.createStudent(dto);
    }
    public CreatedUserDto createTeacher(CreateTeacherDto dto){
        return teacherService.createTeacher(dto);
    }

    public CreatedUserDto resetPassword(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User with id=" + userId + " not found"));

        String newTempPassword = passwordGenerator.generatePassword();

        user.setPassword(passwordEncoder.encode(newTempPassword));
        user.setShouldChangePassword(true);

        userRepository.save(user);

        return new CreatedUserDto(user.getUsername(), newTempPassword);
    }
}
