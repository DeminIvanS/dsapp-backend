package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.PriceListItemDto;
import org.dance.dsappbackend.repository.PriceListItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListItemService {

    private final PriceListItemRepository priceListItemRepository;


    public PriceListItemService(PriceListItemRepository priceListItemRepository) {
        this.priceListItemRepository = priceListItemRepository;
    }


    public PriceListItemDto findById(Long id) {
        return priceListItemRepository.findById(id)
                .map(PriceListItemDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Price list with id=" + id + " not found."));

    }

    public List<PriceListItemDto> findAll(boolean isActive) {
        return priceListItemRepository.findByIsActive(isActive)
                .stream()
                .map(PriceListItemDto::from)
                .toList();
    }

    public PriceListItemDto create(PriceListItemDto dto) {
        var entity = dto.toEntity();
        return PriceListItemDto.from(priceListItemRepository.save(entity));
    }

    public void update(Long id, PriceListItemDto dto) {
        var entity = dto.toEntity();
        entity.setId(id);
        priceListItemRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = priceListItemRepository.findById(id).orElseThrow();
        entity.setActive(false);
        priceListItemRepository.save(entity);
    }


}
