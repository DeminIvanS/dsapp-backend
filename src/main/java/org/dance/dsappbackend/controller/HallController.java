package org.dance.dsappbackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.service.HallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Залы", description = "Управление залами школы")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;

    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить зал по id", description = "возвращает зал по id, доступно только для ROLE_ADMIN")
    public HallDto getById(@PathVariable Long id) {
        return hallService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список залов", description = "возвращает данные по группе, доступно только для ROLE_ADMIN")
    public List<HallDto> getAll() {
        return hallService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый зал", description = "создает новый зал, доступно только для ROLE_ADMIN")
    public HallDto create(@RequestBody HallDto dto) {
        return hallService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить зал по id", description = "изменяет данные зала, доступно только для ROLE_ADMIN")
    public void update(@PathVariable Long id, @RequestBody HallDto dto) {
       hallService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить группу по id", description = "удаляет зал, доступно только для ROLE_ADMIN")
    public void delete(@PathVariable Long id) {
        hallService.delete(id);
    }
}
