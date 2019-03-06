package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.Device;
import org.example.devicenator.domain.DeviceRepository;

public class CreateDevice {
    private DeviceRepository deviceRepository;

    public CreateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(CreateRequestDevice createRequestDevice) {
        Device device = Device.create(createRequestDevice);
        deviceRepository.save(device);
    }
}
