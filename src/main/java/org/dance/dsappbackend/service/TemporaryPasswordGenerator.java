package org.dance.dsappbackend.service;

import java.security.SecureRandom;

public class TemporaryPasswordGenerator {
    private final SecureRandom random = new SecureRandom();

    public String generatePassword() {
        StringBuilder sb = new StringBuilder("KID-");
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
