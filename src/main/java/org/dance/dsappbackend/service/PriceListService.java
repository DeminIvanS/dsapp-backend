package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.PriceListDto;
import org.dance.dsappbackend.repository.PriceListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

    private final PriceListRepository priceListRepository;


    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }


    public PriceListDto findById(Long id){
        return priceListRepository.findById(id)
                .map(PriceListDto::from)
                .orElseThrow(()->new EntityNotFoundException("Price list with id=" +id+" not found."));

    }

    public List<PriceListDto> findAll(){
        return priceListRepository.findAll()
                .stream()
                .map(PriceListDto::from)
                .toList();
    }

    public PriceListDto create(PriceListDto dto){
        var entity = dto.toEntity();
        return PriceListDto.from(priceListRepository.save(entity));
    }

    public void update(Long id, PriceListDto dto){
        var entity = dto.toEntity();
        entity.setId(id);
        priceListRepository.save(entity);
    }

    public void delete(Long id){
        priceListRepository.deleteById(id);
    }

}
