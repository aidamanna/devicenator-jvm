package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.Device;
import org.example.devicenator.domain.DeviceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class DeviceJDBCRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void savesADevice() {
        DeviceRepository deviceRepository = new DeviceJDBCRepository(jdbcTemplate);
        Device device = aDevice();

        deviceRepository.save(device);

        Device savedDevice = deviceRepository.getBy(device.getImei());
        assertThat(savedDevice, is(device));
    }
}