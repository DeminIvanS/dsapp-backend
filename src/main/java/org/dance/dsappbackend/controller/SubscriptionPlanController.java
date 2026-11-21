package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.SubscriptionPlanDto;
import org.dance.dsappbackend.service.SubscriptionPlanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Тарифы (Абонементы)", description = "Управление планами подписок")
@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;

    public SubscriptionPlanController(SubscriptionPlanService planService) {

        this.planService = planService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тариф по id")
    public SubscriptionPlanDto getById(@PathVariable Long id) {
        return planService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список тарифов")
    public List<SubscriptionPlanDto> getAll(@RequestParam(defaultValue = "true")Boolean isActive) {
        return planService.findByIsActive(isActive);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новый тариф")
    public SubscriptionPlanDto create(@RequestBody SubscriptionPlanDto dto) {
        return planService.createSubscriptionPlan(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить тариф")
    public void update(@PathVariable Long id, @RequestBody SubscriptionPlanDto dto) {
       planService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Архивировать тариф")
    public void delete(@PathVariable Long id) {
        planService.delete(id);
    }
}
