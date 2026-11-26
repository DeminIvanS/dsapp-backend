package org.dance.dsappbackend.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.dance.dsappbackend.service.ChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component

public class BillingScheduler {
    private static final Logger log = LoggerFactory.getLogger(BillingScheduler.class);
    private final ChargeService chargeService;

    public BillingScheduler(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @Scheduled(cron = "0 */2 * * * *", zone = "Europe/Moscow")
    public void execute() {
        log.info("Scheduler started at {}", LocalDateTime.now());
        chargeService.createMonthlyCharges();
        log.info("Scheduler executed at {}", LocalDateTime.now());
    }
}
