package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.repository.BranchRepository;
import org.dance.dsappbackend.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;


    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }
    public HallDto findById(Long id){
        return hallRepository.findById(id)
                .map(HallDto::from)
                .orElseThrow(()->new EntityNotFoundException("Hall with id=" +id+" not found."));

    }

    public List<HallDto> findAll(){
        return hallRepository.findAll()
                .stream()
                .map(HallDto::from)
                .toList();
    }

    public HallDto create(HallDto dto){
        var entity = dto.toEntity();
        return HallDto.from(hallRepository.save(entity));
    }

    public void update(Long id, HallDto dto){
        var entity = dto.toEntity();
        entity.setId(id);
        hallRepository.save(entity);
    }

    public void delete(Long id){
        hallRepository.deleteById(id);
    }

}
