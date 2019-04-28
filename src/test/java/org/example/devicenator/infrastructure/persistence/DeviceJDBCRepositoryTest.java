package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.example.devicenator.DeviceFixtures.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeviceJDBCRepositoryTest {

    private DeviceRepository deviceRepository;
    private Flyway flyway;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        DataSource dataSource = dataSourceBuilder.build();

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        jdbcTemplate = new JdbcTemplate(dataSource);

        deviceRepository = new DeviceJDBCRepository(jdbcTemplate);
    }

    @After
    public void tearDown() {
        flyway.clean();
    }

    @Test
    public void savesADevice() throws DeviceAlreadyExists {
        deviceRepository.save(aDevice());

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ?",
                new Object[]{IMEI},
                Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test(expected = DeviceAlreadyExists.class)
    public void throwsExceptionWhenSavingAnExistingDevice() throws DeviceAlreadyExists {
        deviceRepository.save(aDevice());

        deviceRepository.save(aDevice());
    }

    @Test
    public void retrievesADevice() throws Exception {
        Device device = aDevice();

        deviceRepository.save(device);

        Device savedDevice = deviceRepository.getBy(device.getImei());
        assertThat(savedDevice, is(device));
    }


    @Test(expected = DeviceNotFound.class)
    public void throwsExceptionWhenRetrievingANonExistingDevice() throws DeviceNotFound {
        deviceRepository.getBy(UNKNOWN_IMEI);
    }

    @Test
    public void updatesADevice() throws Exception {
        Device device = anUpdatedDevice();

        deviceRepository.save(aDevice());
        deviceRepository.update(device);

        Device updatedDevice = deviceRepository.getBy(device.getImei());
        assertThat(updatedDevice, is(device));
    }
}