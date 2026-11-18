package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.service.ChargeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Tag(name = "Начисления", description = "Ручное начисление и удаление абонементов")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/charges")
public class ChargeController {

    private final ChargeService chargeService;

    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Начислить абонемент ученику")
    public Long create(
            @RequestParam Long studentId,
            @RequestParam Long planId,
            @RequestParam(required = false) Long groupId,
            @RequestParam LocalDate period,
            Principal principal) {
        return chargeService.createCharge(studentId, planId, groupId, period, principal.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Стереть начисление")
    public void delete(@PathVariable Long id) {
        chargeService.deleteCharge(id);
    }
}
