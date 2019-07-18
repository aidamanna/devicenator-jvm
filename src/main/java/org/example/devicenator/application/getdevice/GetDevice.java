package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;

public class GetDevice {

    private DeviceRepository deviceRepository;

    public GetDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device execute(String rawImei) throws DeviceNotFound, InvalidImei {
        Imei imei = Imei.create(rawImei);
        return deviceRepository.getBy(imei);
    }
}
