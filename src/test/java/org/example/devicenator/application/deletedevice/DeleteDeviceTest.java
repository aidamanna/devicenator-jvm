package org.example.devicenator.application.deletedevice;

import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeleteDeviceTest {

    public static final String IMEI = "990000862471853";

    private DeviceJDBCRepository deviceRepository;
    private DeleteDevice deleteDevice;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        deleteDevice = new DeleteDevice(deviceRepository);
    }

    @Test
    public void deletesADevice() throws InvalidImei {
        deleteDevice.execute(IMEI);

        verify(deviceRepository).delete(Imei.create(IMEI));
    }
}