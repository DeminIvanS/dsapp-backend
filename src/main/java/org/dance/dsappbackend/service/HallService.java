package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.repository.BranchRepository;
import org.dance.dsappbackend.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final BranchRepository branchRepository;


    public HallService(HallRepository hallRepository, BranchRepository branchRepository) {
        this.hallRepository = hallRepository;
        this.branchRepository = branchRepository;
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
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(()-> new RuntimeException("Branch not found"));
        var entity = dto.toEntity(branch);
        return HallDto.from(hallRepository.save(entity));
    }

    public void update(Long id, HallDto dto){
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(()-> new RuntimeException("Branch not found"));
        var entity = dto.toEntity(branch);
        entity.setId(id);
        hallRepository.save(entity);
    }

    public void delete(Long id){
        hallRepository.deleteById(id);
    }

}
