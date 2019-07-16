package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.OldDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetDeviceTest {

    public static final String IMEI = "990000862471854";

    private DeviceJDBCRepository deviceRepository;
    private GetDevice getDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        getDevice = new GetDevice(deviceRepository);
    }

    @Test
    public void retrievesAnExistingDevice() throws DeviceNotFound {
        OldDevice expectedDevice = aDevice(IMEI);
        when(deviceRepository.getBy(IMEI)).thenReturn(expectedDevice);

        OldDevice device = getDevice.execute(IMEI);

        assertThat(device, is(expectedDevice));
    }
}