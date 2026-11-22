package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.MonthlyChargeStudentsDto;
import org.dance.dsappbackend.entity.*;
import org.dance.dsappbackend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private final StudentRepository studentRepository;
    private final SubscriptionPlanRepository planRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ChargeService(
            ChargeRepository chargeRepository,
            StudentRepository studentRepository,
            SubscriptionPlanRepository planRepository,
            GroupRepository groupRepository,
            UserRepository userRepository) {
        this.chargeRepository = chargeRepository;
        this.studentRepository = studentRepository;
        this.planRepository = planRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createCharge(Long studentId, Long planId, Long groupId, LocalDate period, String adminUsername) {
        if (period == null) {
            throw new IllegalArgumentException("Расчетный период обязателен");
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan not found"));
        if (!plan.isActive()) {
            throw new IllegalArgumentException("Нельзя начислить архивный тариф");
        }
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        }
        User admin = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
        Charge charge = new Charge();
        charge.setStudent(student);
        charge.setSubscriptionPlan(plan);
        charge.setGroup(group);
        charge.setAmount(plan.getPrice());
        charge.setPeriod(period.withDayOfMonth(1));
        charge.setPaid(false);
        charge.setCreatedBy(admin.getId());

        return chargeRepository.save(charge).getId();
    }

    @Transactional
    public void deleteCharge(Long id) {
        Charge charge = chargeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found"));
        if (charge.isPaid()) {
            throw new IllegalArgumentException("Нельзя удалить уже оплаченное начисление");
        }
        chargeRepository.delete(charge);
    }
    @Transactional
    public void createMonthlyCharges(){
        List<MonthlyChargeStudentsDto> students = chargeRepository.findAllMonthlyChargeStudents();
        LocalDate period = LocalDate.now().withDayOfMonth(1);
        for(MonthlyChargeStudentsDto s:students){
            if(chargeRepository.existsByStudentIdAndGroupIdAndSubscriptionPlanIdAndPeriod(
                    s.studentId(),
                    s.groupId(),
                    s.subscriptionPlanId(),
                    period)){
                continue;
            }
            Student student = studentRepository.findById(s.studentId())
                    .orElseThrow(()-> new EntityNotFoundException("Student not found"));
            Group group = groupRepository.findById(s.groupId())
                    .orElseThrow(()-> new EntityNotFoundException("Group not found"));
            SubscriptionPlan plan = planRepository.findById(s.subscriptionPlanId())
                    .orElseThrow(()-> new EntityNotFoundException("Subscription plan not found"));

            Charge charge = new Charge();
            charge.setStudent(student);
            charge.setSubscriptionPlan(plan);
            charge.setGroup(group);
            charge.setAmount(s.price());
            charge.setPeriod(period);
            charge.setPaid(false);
            charge.setCreatedBy(null);

            chargeRepository.save(charge);
        }

    }
}
