package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;

public class GetDevice {

    private DeviceRepository deviceRepository;

    public GetDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device execute(String imei) throws DeviceNotFound {
        return deviceRepository.getBy(imei);
    }
}
