package org.dance.dsappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DsappBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsappBackendApplication.class, args);
    }

}
