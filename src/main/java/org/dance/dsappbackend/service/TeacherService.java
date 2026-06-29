package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;


    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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

    public TeacherDto create(TeacherDto dto){
        var entity = dto.toEntity();
        return TeacherDto.from(teacherRepository.save(entity));
    }

    public void update(Long id, TeacherDto dto){
        var entity = dto.toEntity();
        entity.setId(id);
        teacherRepository.save(entity);
    }

    public void delete(Long id){
        teacherRepository.deleteById(id);
    }

}
