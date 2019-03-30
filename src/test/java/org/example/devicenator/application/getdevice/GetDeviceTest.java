package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.IMEI;
import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetDeviceTest {

    private DeviceRepository deviceRepository;
    private GetDevice getDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        getDevice = new GetDevice(deviceRepository);
    }

    @Test
    public void retrievesAnExistingDevice() throws DeviceNotFound {
        Device expectedDevice = aDevice();
        when(deviceRepository.getBy(IMEI)).thenReturn(expectedDevice);

        Device device = getDevice.execute(IMEI);

        assertThat(device, is(expectedDevice));
    }
}