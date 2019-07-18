package org.example.devicenator.domain.device;

public class DeviceNotFound extends DeviceException {

    public DeviceNotFound() {
        super("The device is not registered");
    }
}
