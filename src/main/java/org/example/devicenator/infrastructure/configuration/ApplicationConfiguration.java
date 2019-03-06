package org.example.devicenator.infrastructure.configuration;

import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.domain.DeviceRepository;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public CreateDevice createDevice(DeviceRepository deviceRepository) {
        return new CreateDevice(deviceRepository);
    }

    @Bean
    public DeviceJDBCRepository deviceJDBCRepository() {
        return new DeviceJDBCRepository();
    }
}
