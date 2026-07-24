package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {

    private final GroupService groupService;

    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @GetMapping
    public List<GroupDto> getAll() {
        return groupService.findAll();
    }

    @PostMapping
    public GroupDto create(@RequestBody GroupDto dto) {
        return groupService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody GroupDto dto) {
        groupService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }
}
