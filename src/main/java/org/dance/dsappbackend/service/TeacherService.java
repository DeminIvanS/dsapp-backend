package org.dance.dsappbackend.service;

import io.jsonwebtoken.security.Password;
import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
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


    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public TeacherDto findById(Long id){
        return teacherRepository.findById(id)
                .map(TeacherDto::from)
                .orElseThrow(()->new EntityNotFoundException("Teacher with id=" +id+" not found."));

    }

    public List<TeacherDto> findAll(){
        return teacherRepository.findAll()
                .stream()
                .map(TeacherDto::from)
                .toList();
    }
    public TeacherDto registerTeacher(TeacherDto teacherDto, String login, String hashPassword){
        User user = new User();
        user.setUsername(login);
        user.setUsername(passwordEncoder.encode(hashPassword));
        user.setRole(User.Role.ROLE_TEACHER);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        Teacher teacher = teacherDto.toEntity(savedUser);
        teacher.setId(null);
        teacher.setCreatedAt(null);

        Teacher savedTeacher = teacherRepository.save(teacher);

        return TeacherDto.from(savedTeacher);
    }

    public TeacherDto create(TeacherDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        var entity = dto.toEntity(user);
        return TeacherDto.from(teacherRepository.save(entity));
    }

    public void update(Long id, TeacherDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        var entity = dto.toEntity(user);
        entity.setId(id);
        teacherRepository.save(entity);
    }

    public void delete(Long id){
        teacherRepository.deleteById(id);
    }

}
