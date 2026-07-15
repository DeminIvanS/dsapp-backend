package org.dance.dsappbackend.service;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class TemporaryPasswordGenerator {
    private final SecureRandom random = new SecureRandom();
    public String generatePassword() {
        String digits = String.format("%06d", random.nextInt(1000000));
        return "KID-" + digits;
    }
}
