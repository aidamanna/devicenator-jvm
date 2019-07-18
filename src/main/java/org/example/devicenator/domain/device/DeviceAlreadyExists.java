package org.example.devicenator.domain.device;

public class DeviceAlreadyExists extends DeviceException {

    public DeviceAlreadyExists() {
        super("The device is registered");
    }
}
