package org.example.devicenator;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.domain.Device;

public class DeviceFixtures {

    public static Device aDevice() {
        return new Device("990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                "10");
    }

    public static CreateRequestDevice aCreatedRequestDevice() {
        return new CreateRequestDevice(
                "990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                "10");
    }
}
