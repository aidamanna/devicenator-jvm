package org.example.devicenator.application.deletedevice;

import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;

public class DeleteDevice {

    private DeviceRepository deviceRepository;

    public DeleteDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(String imei) throws DeviceNotFound {
        deviceRepository.getBy(imei);

        deviceRepository.delete(imei);
    }
}
