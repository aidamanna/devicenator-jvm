package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.Device;
import org.example.devicenator.domain.DeviceRepository;
import org.junit.Before;
import org.junit.Test;

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
        CreateRequestDevice device = new CreateRequestDevice(
                "990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                10);

        createDevice.execute(device);

        verify(deviceRepository).save(any(Device.class));
    }
}