package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.service.BranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{id}")
    public BranchDto getById(@PathVariable Long id) {
        return branchService.findById(id);
    }

    @GetMapping
    public List<BranchDto> getAll() {
        return branchService.findAll();
    }

    @PostMapping
    public BranchDto create(@RequestBody BranchDto dto) {
        return branchService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody BranchDto dto) {
       branchService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        branchService.delete(id);
    }
}
