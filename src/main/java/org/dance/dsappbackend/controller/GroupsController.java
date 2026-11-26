package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.CreateGroupDto;
import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.service.GroupService;
import org.dance.dsappbackend.service.SubscriptionPlanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы", description = "Управление группами школы")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/groups")
public class GroupsController {

    private final GroupService groupService;

    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
            }

    @GetMapping("/{id}")
    @Operation(summary = "Получить группу по id", description = "возвращает группу по id, доступно только для ROLE_ADMIN")
    public GroupDto getById(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Получить список групп", description = "возвращает список групп, доступно только для ROLE_ADMIN")
    public List<GroupDto> getAll() {
        return groupService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новую группу", description = "создает новую группу, доступно только для ROLE_ADMIN")
    public GroupDto create(@RequestBody CreateGroupDto dto) {
        return groupService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить группу по id", description = "изменяет данные группы, доступно только для ROLE_ADMIN")
    public void update(@PathVariable Long id, @RequestBody CreateGroupDto dto) {
       groupService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить группу по id", description = "удаляет группу, доступно только для ROLE_ADMIN")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @PostMapping("/{groupId}/subscription-plans/{planId}")
    @Operation(summary = "Привязать абонемент к группе", description = "Привязывает базовый абонемент к группе, доступно только для ROLE_ADMIN")
    public void addSubscriptionPlan(@PathVariable Long groupId, @PathVariable Long planId){
        groupService.addSubscriptionPlan(groupId, planId);

    }
}
