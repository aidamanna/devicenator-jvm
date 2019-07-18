package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceException;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.fixtures.DeviceFixtures.aCreateRequestDevice;
import static org.example.devicenator.fixtures.DeviceFixtures.aDevice;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateDeviceTest {

    private static final String RAW_IMEI = "990000862471853";

    private DeviceJDBCRepository deviceRepository;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        createDevice = new CreateDevice(deviceRepository);
    }

    @Test
    public void createsADevice() throws DeviceException {
        createDevice.execute(aCreateRequestDevice(RAW_IMEI));

        Device device = aDevice(RAW_IMEI);
        verify(deviceRepository).save(device);
    }
}