package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.domain.device.OldDevice;
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

    public static final String IMEI = "990000862471853";
    public static final String UNKNOWN_IMEI = "99000086247185";
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
    public void savesAnOldDevice() throws DeviceAlreadyExists {
        deviceRepository.save(anOldDevice(IMEI));

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ?",
                new Object[]{IMEI},
                Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test
    public void savesADevice() throws DeviceAlreadyExists, InvalidImei {
        deviceRepository.save(aDevice(IMEI));

        Integer deviceCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM devices WHERE imei = ?",
            new Object[]{IMEI},
            Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test(expected = DeviceAlreadyExists.class)
    public void throwsExceptionWhenSavingAnOldExistingDevice() throws DeviceAlreadyExists {
        OldDevice device = anOldDevice(IMEI);
        deviceRepository.save(device);

        deviceRepository.save(device);
    }

    @Test(expected = DeviceAlreadyExists.class)
    public void throwsExceptionWhenSavingAExistingDevice() throws DeviceAlreadyExists, InvalidImei {
        Device device = aDevice(IMEI);
        deviceRepository.save(device);

        deviceRepository.save(device);
    }

    @Test
    public void retrievesADevice() throws Exception {
        OldDevice device = anOldDevice(IMEI);

        deviceRepository.save(device);

        OldDevice savedDevice = deviceRepository.getBy(IMEI);
        assertThat(savedDevice, is(device));
    }


    @Test(expected = DeviceNotFound.class)
    public void throwsExceptionWhenRetrievingANonExistingDevice() throws DeviceNotFound {
        deviceRepository.getBy(UNKNOWN_IMEI);
    }

    @Test
    public void updatesADevice() throws Exception {
        deviceRepository.save(anOldDevice(IMEI, OPERATING_SYSTEM_VERSION_10));

        deviceRepository.update(anOldDevice(IMEI, OPERATING_SYSTEM_VERSION_11));

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ? AND operatingSystemVersion = ?",
                new Object[]{IMEI, OPERATING_SYSTEM_VERSION_11},
                Integer.class);

        assertThat(deviceCount, is(1));
    }

    @Test
    public void deletesADevice() throws DeviceAlreadyExists {
        deviceRepository.save(anOldDevice(IMEI));

        deviceRepository.delete(IMEI);

        Integer deviceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM devices WHERE imei = ?",
                new Object[]{IMEI},
                Integer.class);

        assertThat(deviceCount, is(0));
    }

    @Test
    public void doesNotThrowAnExceptionWhenDeletingNonExistingDevices() {
        deviceRepository.delete(UNKNOWN_IMEI);

        Integer deviceCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM devices WHERE imei = ?",
            new Object[]{IMEI},
            Integer.class);

        assertThat(deviceCount, is(0));
    }
}