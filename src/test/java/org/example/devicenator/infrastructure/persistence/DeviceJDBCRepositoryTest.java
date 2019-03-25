package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.Device;
import org.example.devicenator.domain.DeviceRepository;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.infrastructure.configuration.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, classes = ApplicationConfiguration.class)
@EnableAutoConfiguration
public class DeviceJDBCRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DeviceRepository deviceRepository;

    @Before
    public void setUp() {
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