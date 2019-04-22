package org.example.devicenator.application.updatedevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.example.devicenator.DeviceFixtures.anUpdateRequestDevice;
import static org.example.devicenator.DeviceFixtures.anUpdatedDevice;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateDeviceTest {

    DeviceRepository deviceRepository;
    UpdateDevice updateDevice;
    UpdateRequestDevice device;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceRepository.class);
        updateDevice = new UpdateDevice(deviceRepository);

        device = anUpdateRequestDevice();
    }

    @Test
    public void updatesADevice() throws DeviceNotFound {
        when(deviceRepository.getBy(device.getImei())).thenReturn(aDevice());

        updateDevice.execute(anUpdateRequestDevice());

        verify(deviceRepository).update(anUpdatedDevice());
    }

    @Test(expected = DeviceNotFound.class)
    public void throwsAnExceptionWhenUpdatingANonExistingDevice() throws DeviceNotFound {
        when(deviceRepository.getBy(device.getImei())).thenThrow(DeviceNotFound.class);

        updateDevice.execute(device);

        verify(deviceRepository, times(0));
    }
}