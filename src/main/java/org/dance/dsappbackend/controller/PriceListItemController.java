package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.PriceListItemDto;
import org.dance.dsappbackend.service.PriceListItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price_list_items")
public class PriceListItemController {

    private final PriceListItemService priceListItemService;

    public PriceListItemController(PriceListItemService priceListItemService) {

        this.priceListItemService = priceListItemService;
    }

    @GetMapping("/{id}")
    public PriceListItemDto getById(@PathVariable Long id) {
        return priceListItemService.findById(id);
    }

    @GetMapping
    public List<PriceListItemDto> getAll(@RequestParam(defaultValue = "true")Boolean isActive) {
        return priceListItemService.findByIsActive(isActive);
    }

    @PostMapping
    public PriceListItemDto create(@RequestBody PriceListItemDto dto) {
        return priceListItemService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PriceListItemDto dto) {
       priceListItemService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        priceListItemService.delete(id);
    }
}
