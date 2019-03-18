package org.example.devicenator.infrastructure.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.devicenator.infrastructure"})
public class DevicenatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevicenatorApplication.class, args);
    }

}
