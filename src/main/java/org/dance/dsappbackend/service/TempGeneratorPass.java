package org.dance.dsappbackend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TempGeneratorPass {
    public static void main(String[] args) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = "admin"; // Напиши свой пароль тут
            String hash = encoder.encode(rawPassword);
            System.out.println("ТВОЙ ХЕШ ДЛЯ БАЗЫ: " + hash);
        }



}
