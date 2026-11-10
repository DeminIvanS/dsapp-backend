package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.PriceListItemDto;
import org.dance.dsappbackend.entity.PriceListItem;
import org.dance.dsappbackend.repository.PriceListItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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

    public List<PriceListItemDto> findByIsActive(boolean isActive) {
        return priceListItemRepository.findByIsActive(isActive)
                .stream()
                .map(PriceListItemDto::from)
                .toList();
    }
    @Transactional
    public PriceListItemDto create(PriceListItemDto dto) {
        if(!StringUtils.hasText(dto.getName())){
            throw new IllegalArgumentException("The name of a price list item cannot be empty");
        }
        if(dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <=0) {
            throw new IllegalArgumentException("The price of a price list item must be strictly greater than zero.");
        }


        var entity = dto.toEntity();
        return PriceListItemDto.from(priceListItemRepository.save(entity));
    }

    @Transactional
    public void update(Long id, PriceListItemDto dto) {
        if(!StringUtils.hasText(dto.getName())){
            throw new IllegalArgumentException("The name of a price list item cannot be empty");
        }
        if(dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <=0) {
            throw new IllegalArgumentException("The price of a price list item must be strictly greater than zero.");
        }
        PriceListItem entity = priceListItemRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Not found"));

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());
        priceListItemRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = priceListItemRepository.findById(id).orElseThrow();
        entity.setActive(false);
        priceListItemRepository.save(entity);
    }


}
