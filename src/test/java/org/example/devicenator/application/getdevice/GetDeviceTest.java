package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.*;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.fixtures.DeviceFixtures.aDevice;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetDeviceTest {

    private static final String RAW_IMEI = "990000862471853";

    private DeviceJDBCRepository deviceRepository;
    private GetDevice getDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        getDevice = new GetDevice(deviceRepository);
    }

    @Test
    public void retrievesAnExistingDevice() throws DeviceException {
        Device expectedDevice = aDevice(RAW_IMEI);
        when(deviceRepository.getBy(Imei.create(RAW_IMEI))).thenReturn(expectedDevice);

        Device device = getDevice.execute(RAW_IMEI);

        assertThat(device, is(expectedDevice));
    }
}