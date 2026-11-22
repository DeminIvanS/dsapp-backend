package org.dance.dsappbackend.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.dance.dsappbackend.service.ChargeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class BillingScheduler {
    private final ChargeService chargeService;

    public BillingScheduler(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @Scheduled(cron = "0 */2 * * * *", zone = "Europe/Moscow")
    public void execute() {
        chargeService.createMonthlyCharges();
        log.info("Scheduler executed at {}", LocalDateTime.now());
    }
}
