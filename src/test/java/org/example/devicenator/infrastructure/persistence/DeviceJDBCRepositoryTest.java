package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;
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

    public static final String RAW_IMEI = "990000862471853";
    public static final String UNKNOWN_RAW_IMEI = "990000862471853";
    public static final String OPERATING_SYSTEM_VERSION_10 = "10";
    public static final String OPERATING_SYSTEM_VERSION_11 = "11";

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
    public void savesADevice() throws DeviceAlreadyExists, InvalidImei {
        deviceRepository.save(aDevice(RAW_IMEI));

        Integer deviceCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM devices WHERE imei = ?",
            new Object[]{RAW_IMEI},
            Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test(expected = DeviceAlreadyExists.class)
    public void throwsExceptionWhenSavingAnExistingDevice() throws DeviceAlreadyExists, InvalidImei {
        Device device = aDevice(RAW_IMEI);
        deviceRepository.save(device);

        deviceRepository.save(device);
    }

    @Test
    public void retrievesADevice() throws Exception {
        Device device = aDevice(RAW_IMEI);

        deviceRepository.save(device);

        Device savedDevice = deviceRepository.getBy(Imei.create(RAW_IMEI));
        assertThat(savedDevice, is(device));
    }

    @Test(expected = DeviceNotFound.class)
    public void throwsExceptionWhenRetrievingANonExistingDevice() throws DeviceNotFound, InvalidImei {
        deviceRepository.getBy(Imei.create(UNKNOWN_RAW_IMEI));
    }

    @Test
    public void updatesADevice() throws Exception {
        deviceRepository.save(aDevice(RAW_IMEI, OPERATING_SYSTEM_VERSION_10));

        deviceRepository.update(aDevice(RAW_IMEI, OPERATING_SYSTEM_VERSION_11));

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ? AND operatingSystemVersion = ?",
                new Object[]{RAW_IMEI, OPERATING_SYSTEM_VERSION_11},
                Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test
    public void deletesADevice() throws DeviceAlreadyExists, InvalidImei {
        deviceRepository.save(aDevice(RAW_IMEI));

        deviceRepository.delete(Imei.create(RAW_IMEI));

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ?",
                new Object[]{RAW_IMEI},
                Integer.class);

        assertThat(deviceCount, is(0));
    }

    @Test
    public void doesNotThrowAnExceptionWhenDeletingNonExistingDevice() throws InvalidImei {
        deviceRepository.delete(Imei.create(UNKNOWN_RAW_IMEI));

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ?",
                new Object[]{RAW_IMEI},
                Integer.class);

        assertThat(deviceCount, is(0));
    }
}