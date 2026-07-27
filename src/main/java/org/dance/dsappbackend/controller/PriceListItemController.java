package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.PriceListDto;
import org.dance.dsappbackend.service.PriceListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price_list_items")
public class PriceListItemController {

    private final PriceListService priceListService;

    public PriceListItemController(PriceListService priceListService) {

        this.priceListService = priceListService;
    }

    @GetMapping("/{id}")
    public PriceListDto getById(@PathVariable Long id) {
        return priceListService.findById(id);
    }

    @GetMapping
    public List<PriceListDto> getAll() {
        return priceListService.findAll();
    }

    @PostMapping
    public PriceListDto create(@RequestBody PriceListDto dto) {
        return priceListService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PriceListDto dto) {
       priceListService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        priceListService.delete(id);
    }
}
