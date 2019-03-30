package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.aCreatedRequestDevice;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateDeviceTest {

    private DeviceRepository deviceRepository;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        createDevice = new CreateDevice(deviceRepository);
    }

    @Test
    public void createsADevice() {
        createDevice.execute(aCreatedRequestDevice());

        verify(deviceRepository).save(any(Device.class));
    }
}