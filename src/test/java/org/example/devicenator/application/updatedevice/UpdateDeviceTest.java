package org.example.devicenator.application.updatedevice;

import org.example.devicenator.DeviceFixtures;
import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.*;
import static org.mockito.Mockito.*;

public class UpdateDeviceTest {

    public static final String IMEI = "990000862471854";
    public static final String UNKNOWN_IMEI = "99000086247185";
    public static final String OPERATING_SYSTEM_VERSION = "11";

    DeviceJDBCRepository deviceRepository;
    UpdateDevice updateDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        updateDevice = new UpdateDevice(deviceRepository);
    }

    @Test
    public void updatesADevice() throws DeviceNotFound {
        Device device = aDevice(IMEI, OPERATING_SYSTEM_VERSION);
        when(deviceRepository.getBy(IMEI)).thenReturn(device);

        updateDevice.execute(IMEI, anUpdateRequestDevice(OPERATING_SYSTEM_VERSION));

        verify(deviceRepository).update(device);
    }

    @Test(expected = DeviceNotFound.class)
    public void throwsAnExceptionWhenUpdatingANonExistingDevice() throws DeviceNotFound {
        when(deviceRepository.getBy(UNKNOWN_IMEI)).thenThrow(DeviceNotFound.class);

        updateDevice.execute(UNKNOWN_IMEI, anUpdateRequestDevice(OPERATING_SYSTEM_VERSION));

        verify(deviceRepository, times(0))
                .update(DeviceFixtures.aDevice(UNKNOWN_IMEI, OPERATING_SYSTEM_VERSION));
    }
}