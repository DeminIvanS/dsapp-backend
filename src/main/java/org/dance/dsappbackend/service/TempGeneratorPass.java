package org.dance.dsappbackend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TempGeneratorPass {
        public static String passGenerate() {
            List<Integer> digits = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            Collections.shuffle(digits);
            String pass = "KID-";
            int num;
            for (int i = 0; i < 6; i++) {
                Collections.shuffle(digits);
                num = digits.get(i);
                pass = pass + num;
            }
            return pass;
        }
}
