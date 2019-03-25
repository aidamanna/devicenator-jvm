package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.Device;
import org.example.devicenator.domain.DeviceRepository;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeviceJDBCRepositoryTest {

    private DeviceRepository deviceRepository;

    @Before
    public void setUp() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        DataSource dataSource = dataSourceBuilder.build();

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        deviceRepository = new DeviceJDBCRepository(jdbcTemplate);
    }


    @Test
    public void savesADevice() throws DeviceNotFound {
        Device device = aDevice();

        deviceRepository.save(device);

        Device savedDevice = deviceRepository.getBy(device.getImei());
        assertThat(savedDevice, is(device));
    }


    @Test(expected = DeviceNotFound.class)
    public void throwsExceptionWhenRetrievingANonExistingDevice() throws DeviceNotFound {
        deviceRepository.getBy("99000086247185");
    }

}