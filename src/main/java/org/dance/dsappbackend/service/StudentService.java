package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.repository.StudentRepository;
import org.dance.dsappbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public StudentService(StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public StudentDto findById(Long id){
        return studentRepository.findById(id)
                .map(StudentDto::from)
                .orElseThrow(()->new EntityNotFoundException("Student with id=" +id+" not found."));

    }

    public List<StudentDto> findAll(){
        return studentRepository.findAll()
                .stream()
                .map(StudentDto::from)
                .toList();
    }

    @Transactional
    public CreatedUserDto createStudent(CreateStudentDto dto){
        String tempPassword = "12345";
        String passwordHash = passwordEncoder.encode(tempPassword);//TODO: генератор пароля
        User user = dto.toUserEntity(passwordHash);
        User savedUser = userRepository.save(user);
        Student student = dto.toStudentEntity(savedUser);
        Student savedStudent = studentRepository.save(student);
        return new CreatedUserDto(savedUser.getUsername(),tempPassword);
    }

    public void update(Long id, StudentDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        var entity = dto.toEntity(user);
        entity.setId(id);
        studentRepository.save(entity);
    }

    public void delete(Long id){
        studentRepository.deleteById(id);
    }

}
