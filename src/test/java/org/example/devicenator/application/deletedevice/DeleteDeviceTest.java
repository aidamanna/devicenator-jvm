package org.example.devicenator.application.deletedevice;

import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeleteDeviceTest {

    public static final String IMEI = "990000862471854";
    public static final String UNKNOWN_IMEI = "990000862471855";

    private DeviceJDBCRepository deviceRepository;
    private DeleteDevice deleteDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        deleteDevice = new DeleteDevice(deviceRepository);
    }

    @Test
    public void deletesADevice() throws DeviceNotFound {
        deleteDevice.execute(IMEI);

        verify(deviceRepository).delete(IMEI);
    }

    @Test(expected = DeviceNotFound.class)
    public void throwsAnExceptionWhenDeletingANonExistingDevice() throws DeviceNotFound {
        when(deviceRepository.getBy(UNKNOWN_IMEI)).thenThrow(DeviceNotFound.class);

        deleteDevice.execute(UNKNOWN_IMEI);

        verify(deviceRepository, times(0)).delete(UNKNOWN_IMEI);
    }
}