package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.mapper.StudentMapper;
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
    private final TemporaryPasswordGenerator passwordGenerator;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TemporaryPasswordGenerator passwordGenerator, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.studentMapper = studentMapper;
    }

    public StudentDto findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toStudentDto)
                .orElseThrow(() -> new EntityNotFoundException("Student with id=" + id + " not found."));

    }

    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentDto)
                .toList();
    }

    @Transactional
    public CreatedUserDto createStudent(CreateStudentDto dto) {
        String tempPassword = passwordGenerator.generatePassword();
        String passwordHash = passwordEncoder.encode(tempPassword);
        User user = dto.toUserEntity(passwordHash);
        User savedUser = userRepository.save(user);
        Student student = dto.toStudentEntity(savedUser);
        Student savedStudent = studentRepository.save(student);
        return new CreatedUserDto(savedUser.getUsername(), tempPassword);
    }

    public void update(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id=" + id + " not found"));
        studentMapper.updateEntityFromDto(dto,student);

        studentRepository.save(student);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

}
