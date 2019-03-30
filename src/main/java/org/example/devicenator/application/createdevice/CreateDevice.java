package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceRepository;

public class CreateDevice {
    private DeviceRepository deviceRepository;

    public CreateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(CreateRequestDevice createRequestDevice) {
        Device device = new Device(
                createRequestDevice.getImei(),
                createRequestDevice.getVendor(),
                createRequestDevice.getModel(),
                createRequestDevice.getOperatingSystem(),
                createRequestDevice.getOperatingSystemVersion());

        deviceRepository.save(device);
    }
}
