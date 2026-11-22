package org.dance.dsappbackend.repository;

import org.dance.dsappbackend.dto.MonthlyChargeStudentsDto;
import org.dance.dsappbackend.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {

    @Query("""
        SELECT new org.dance.dsappbackend.dto.MonthlyChargeStudentsDto(
            s.id,
            g.id,
            p.id,
            p.price
        )
        FROM Group g
        JOIN g.students s
        JOIN g.subscriptionPlans p
        WHERE p.isActive = true
          AND p.billingMode = org.dance.dsappbackend.enums.BillingMode.AUTO
          AND s.user.isActive = true
        """)
    List<MonthlyChargeStudentsDto> findAllMonthlyChargeStudents();
    boolean existsByStudentIdAndGroupIdAndSubscriptionPlanIdAndPeriod(Long studentId, Long groupId, Long subscriptionPlanId, LocalDate period);
}
