package org.example.devicenator.application.deletedevice;

import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;

public class DeleteDevice {

    private DeviceRepository deviceRepository;

    public DeleteDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(String rawImei) throws InvalidImei {
        Imei imei = Imei.create(rawImei);
        deviceRepository.delete(imei);
    }
}
