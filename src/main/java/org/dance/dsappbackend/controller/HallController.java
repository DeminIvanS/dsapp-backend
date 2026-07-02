package org.dance.dsappbackend.controller;


import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.service.HallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;

    }

    @GetMapping("/{id}")
    public HallDto getById(@PathVariable Long id) {
        return hallService.findById(id);
    }

    @GetMapping
    public List<HallDto> getAll() {
        return hallService.findAll();
    }

    @PostMapping
    public HallDto create(@RequestBody HallDto dto) {
        return hallService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody HallDto dto) {
       hallService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hallService.delete(id);
    }
}
