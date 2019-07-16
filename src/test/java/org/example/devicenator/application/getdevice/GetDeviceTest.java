package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;
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

    public static final String IMEI = "990000862471853";

    private DeviceJDBCRepository deviceRepository;
    private GetDevice getDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        getDevice = new GetDevice(deviceRepository);
    }

    @Test
    public void retrievesAnExistingDevice() throws DeviceNotFound, InvalidImei {
        Device expectedDevice = aDevice(IMEI);
        when(deviceRepository.getBy(Imei.create(IMEI))).thenReturn(expectedDevice);

        Device device = getDevice.execute(IMEI);

        assertThat(device, is(expectedDevice));
    }
}