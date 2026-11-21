package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.CreateHallDto;
import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Hall;
import org.dance.dsappbackend.mapper.HallMapper;
import org.dance.dsappbackend.repository.BranchRepository;
import org.dance.dsappbackend.repository.HallRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final BranchRepository branchRepository;
    private final HallMapper hallMapper;


    public HallService(HallRepository hallRepository, BranchRepository branchRepository, HallMapper hallMapper) {
        this.hallRepository = hallRepository;
        this.branchRepository = branchRepository;
        this.hallMapper = hallMapper;
    }
    public HallDto findById(Long id){
        return hallRepository.findById(id)
                .map(hallMapper::toHallDto)
                .orElseThrow(()->new EntityNotFoundException("Hall with id=" +id+" not found."));

    }

    public List<HallDto> findAll(){
        return hallRepository.findAll()
                .stream()
                .map(hallMapper::toHallDto)
                .toList();
    }
    @Transactional
    public HallDto createHall(CreateHallDto dto){
        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new EntityNotFoundException("Branch not found"));
        Hall hall = hallMapper.toHallEntity(dto, branch);
        Hall saved = hallRepository.save(hall);
        return hallMapper.toHallDto(saved);
    }

    @Transactional
    public void update(Long id, HallDto dto){

        Hall hall = hallRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Hall not found"));
        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new EntityNotFoundException("Branch not found"));
        hall.setBranch(branch);
        hall.setName(dto.name());
        hall.setDescription(dto.description());
        hallRepository.save(hall);
    }

    public void delete(Long id){
        hallRepository.deleteById(id);
    }

}
