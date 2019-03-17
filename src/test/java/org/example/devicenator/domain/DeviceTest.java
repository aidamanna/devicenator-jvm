package org.example.devicenator.domain;

import org.junit.Test;

import static org.example.devicenator.DeviceFixtures.aCreatedRequestDevice;
import static org.example.devicenator.DeviceFixtures.aDevice;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DeviceTest {

    @Test
    public void createsADeviceFromACreateRequestDevice() {
        Device device = Device.create(aCreatedRequestDevice());

        assertThat(device, is(aDevice()));
    }
}