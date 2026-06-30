package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.repository.StudentRepository;
import org.dance.dsappbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;


    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
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

    public StudentDto create(StudentDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        var entity = dto.toEntity(user);
        return StudentDto.from(studentRepository.save(entity));
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
