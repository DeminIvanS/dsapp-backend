package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.dto.CreateBranchDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.mapper.BranchMapper;
import org.dance.dsappbackend.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;


    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }
    public BranchDto findById(Long id){
        return branchRepository.findById(id)
                .map(branchMapper::toBranchDto)
                .orElseThrow(()->new EntityNotFoundException("Branch with id=" +id+" not found."));

    }

    public List<BranchDto> findAll(){
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toBranchDto)
                .toList();
    }

    public BranchDto create(CreateBranchDto dto){
        var entity = branchMapper.toBranchEntity(dto);
        Branch savedEntity = branchRepository.save(entity);
        return branchMapper.toBranchDto(savedEntity);
    }

    public void update(Long id, CreateBranchDto dto){
        var entity = branchMapper.toBranchEntity(dto);

        entity.setId(id);
        branchRepository.save(entity);
    }

    public void delete(Long id){
        branchRepository.deleteById(id);
    }

}
