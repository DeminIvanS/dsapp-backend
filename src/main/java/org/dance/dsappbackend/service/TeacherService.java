package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.mappers.TeacherMapper;
import org.dance.dsappbackend.repository.TeacherRepository;
import org.dance.dsappbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemporaryPasswordGenerator passwordGenerator;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TemporaryPasswordGenerator passwordGenerator) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
    }

    public TeacherDto findById(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id=" + id + " not found."));

    }

    public List<TeacherDto> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherDto::from)
                .toList();
    }

    @Transactional
    public CreatedUserDto createTeacher(TeacherMapper dto) {
        String tempPassword = passwordGenerator.generatePassword();
        String passwordHash = passwordEncoder.encode(tempPassword);
        User user = dto.toUserEntity(passwordHash);
        User savedUser = userRepository.save(user);
        Teacher teacher = dto.toTeacherEntity(savedUser);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return new CreatedUserDto(savedUser.getUsername(), tempPassword);
    }

    public void update(Long id, TeacherDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var entity = dto.toEntity(user);
        entity.setId(id);
        teacherRepository.save(entity);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

}
