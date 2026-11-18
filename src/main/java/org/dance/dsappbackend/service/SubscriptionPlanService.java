package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.SubscriptionPlanDto;
import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.dance.dsappbackend.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;


    public SubscriptionPlanService(SubscriptionPlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    public SubscriptionPlanDto findById(Long id) {
        return planRepository.findById(id)
                .map(SubscriptionPlanDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));

    }

    public List<SubscriptionPlanDto> findByIsActive(boolean isActive) {
        return planRepository.findByIsActive(isActive)
                .stream()
                .map(SubscriptionPlanDto::from)
                .toList();
    }
    @Transactional
    public SubscriptionPlanDto create(SubscriptionPlanDto dto) {
        validateDto(dto);
        var entity = dto.toEntity();
        return SubscriptionPlanDto.from(planRepository.save(entity));
    }

    @Transactional
    public void update(Long id, SubscriptionPlanDto dto) {
        validateDto(dto);
        SubscriptionPlan entity = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setBillingMode(dto.getBillingMode());
        entity.setActive(dto.isActive());

        planRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));
        entity.setActive(false);
        planRepository.save(entity);
    }
    private void validateDto(SubscriptionPlanDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new IllegalArgumentException("The subscription name cannot be empty");
        }
        if (dto.getPrice() == null || dto.getPrice().signum() != 1) {
            throw new IllegalArgumentException("The price of a subscription must be greater than zero.");
        }
        if (dto.getBillingMode() == null) {
            throw new IllegalArgumentException("The accrual mode (AUTO or MANUAL) is mandatory.");
        }
    }


}
