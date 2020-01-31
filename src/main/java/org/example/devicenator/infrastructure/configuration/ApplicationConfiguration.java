package org.example.devicenator.infrastructure.configuration;

import org.example.devicenator.application.authenticateuser.GetJwtUser;
import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.application.registeruser.RegisterUser;
import org.example.devicenator.application.deletedevice.DeleteDevice;
import org.example.devicenator.application.getdevice.GetDevice;
import org.example.devicenator.application.listdevices.ListDevices;
import org.example.devicenator.application.updatedevice.UpdateDevice;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.user.JwtToken;
import org.example.devicenator.domain.user.UserRepository;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.example.devicenator.infrastructure.persistence.UserJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public CreateDevice createDevice(DeviceRepository deviceRepository) {
        return new CreateDevice(deviceRepository);
    }

    @Bean
    public GetDevice getDevice(DeviceRepository deviceRepository) {
        return new GetDevice(deviceRepository);
    }

    @Bean
    public ListDevices listDevices(DeviceRepository deviceRepository) {
        return new ListDevices(deviceRepository);
    }

    @Bean
    public UpdateDevice updateDevice(DeviceRepository deviceRepository) {
        return new UpdateDevice(deviceRepository);
    }

    @Bean
    public DeleteDevice deleteDevice(DeviceRepository deviceRepository) {
        return new DeleteDevice(deviceRepository);
    }

    @Bean
    public DeviceJDBCRepository deviceJDBCRepository(JdbcTemplate jdbcTemplate) {
        return new DeviceJDBCRepository(jdbcTemplate);
    }

    @Bean
    public RegisterUser createUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new RegisterUser(userRepository, passwordEncoder);
    }

    @Bean
    public UserJdbcRepository userJdbcRepository(JdbcTemplate jdbcTemplate) {
        return new UserJdbcRepository(jdbcTemplate);
    }

    @Bean
    public JwtToken jwtToken() {
        return new JwtToken();
    }

    @Bean
    public GetJwtUser userDetailsService(UserRepository userRepository, JwtToken jwtToken) {
        return new GetJwtUser(userRepository, jwtToken);
    }
}
