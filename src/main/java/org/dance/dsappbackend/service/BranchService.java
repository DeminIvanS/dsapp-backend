package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;


    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public BranchDto findById(Long id) {
        return branchRepository.findById(id)
                .map(BranchDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Branch with id=" + id + " not found."));

    }

    public List<BranchDto> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(BranchDto::from)
                .toList();
    }

    public BranchDto create(BranchDto dto) {
        var entity = dto.toEntity();
        return BranchDto.from(branchRepository.save(entity));
    }

    public void update(Long id, BranchDto dto) {
        var entity = dto.toEntity();
        entity.setId(id);
        branchRepository.save(entity);
    }

    public void delete(Long id) {
        branchRepository.deleteById(id);
    }

}
