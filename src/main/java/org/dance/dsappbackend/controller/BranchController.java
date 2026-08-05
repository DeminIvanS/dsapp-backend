package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.dto.CreateBranchDto;
import org.dance.dsappbackend.service.BranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Филиалы", description = "Управление филиалами школы")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить филиал по id", description = "возвращает филиал по id, доступно только для ROLE_ADMIN")
    public BranchDto getById(@PathVariable Long id) {
        return branchService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список филиалов", description = "возвращает список филиалов, доступно только для ROLE_ADMIN")
    public List<BranchDto> getAll() {
        return branchService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый филиал", description = "создает новый филиал, доступно только для ROLE_ADMIN")
    public BranchDto create(@RequestBody CreateBranchDto dto) {
        return branchService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить филиалу по id", description = "изменяет данные по филиалу, доступно только для ROLE_ADMIN")
    public void update(@PathVariable Long id, @RequestBody CreateBranchDto dto) {
       branchService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить филиал по id", description = "удаляет филиал, доступно только для ROLE_ADMIN")
    public void delete(@PathVariable Long id) {
        branchService.delete(id);
    }
}
