package org.example.devicenator.infrastructure.configuration;

import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.domain.DeviceRepository;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public CreateDevice createDevice(DeviceRepository deviceRepository) {
        return new CreateDevice(deviceRepository);
    }

    @Bean
    public DeviceJDBCRepository deviceJDBCRepository(JdbcTemplate jdbcTemplate) {
        return new DeviceJDBCRepository(jdbcTemplate);
    }
}
