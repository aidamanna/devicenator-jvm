package org.example.devicenator.domain;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DeviceTest {

    @Test
    public void createsADeviceFromACreateRequestDevice() {
        CreateRequestDevice createRequestDevice = new CreateRequestDevice(
                "990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                10);

        Device expectedDevice = new Device("990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                10);

        Device device = Device.create(createRequestDevice);
        assertThat(device, is(expectedDevice));
    }
}