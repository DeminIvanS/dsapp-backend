package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.SubscriptionPlanDto;
import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.dance.dsappbackend.mapper.SubscriptionPlanMapper;
import org.dance.dsappbackend.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;
    private final SubscriptionPlanMapper planMapper;


    public SubscriptionPlanService(SubscriptionPlanRepository planRepository,SubscriptionPlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }


    public SubscriptionPlanDto findById(Long id) {
        return planRepository.findById(id)
                .map(planMapper::toSubscriptionPlanDto)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));

    }

    public List<SubscriptionPlanDto> findByIsActive(boolean isActive) {
        return planRepository.findByIsActive(isActive)
                .stream()
                .map(planMapper::toSubscriptionPlanDto)
                .toList();
    }
    @Transactional
    public SubscriptionPlanDto createSubscriptionPlan(SubscriptionPlanDto dto) {
        validateDto(dto);
        SubscriptionPlan entity = planMapper.toSubscriptionPlanEntity(dto);
        SubscriptionPlan saved = planRepository.save(entity);
        return planMapper.toSubscriptionPlanDto(saved);
    }

    @Transactional
    public void update(Long id, SubscriptionPlanDto dto) {
        validateDto(dto);
        SubscriptionPlan entity = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));

        entity.setName(dto.name());
        entity.setPrice(dto.price());
        entity.setBillingMode(dto.billingMode());
        entity.setActive(dto.isActive());

        planRepository.save(entity);
    }
    @Transactional
    public void delete(Long id) {
        var entity = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan with id=" + id + " not found."));
        entity.setActive(false);
        planRepository.save(entity);
    }
    private void validateDto(SubscriptionPlanDto dto) {
        if (!StringUtils.hasText(dto.name())) {
            throw new IllegalArgumentException("The subscription name cannot be empty");
        }
        if (dto.price() == null || dto.price().signum() != 1) {
            throw new IllegalArgumentException("The price of a subscription must be greater than zero.");
        }
        if (dto.billingMode() == null) {
            throw new IllegalArgumentException("The accrual mode (AUTO or MANUAL) is mandatory.");
        }
    }


}
