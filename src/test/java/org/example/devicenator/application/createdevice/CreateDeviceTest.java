package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.aCreateRequestDevice;
import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateDeviceTest {

    public static final String IMEI = "990000862471853";

    private DeviceJDBCRepository deviceRepository;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        createDevice = new CreateDevice(deviceRepository);
    }

    @Test
    public void createsADevice() throws DeviceAlreadyExists, InvalidImei {
        createDevice.execute(aCreateRequestDevice(IMEI));

        Device device = aDevice(IMEI);
        verify(deviceRepository).save(device);
    }
}