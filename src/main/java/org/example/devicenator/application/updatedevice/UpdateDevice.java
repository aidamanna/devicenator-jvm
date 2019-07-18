package org.example.devicenator.application.updatedevice;

import org.example.devicenator.domain.device.*;


public class UpdateDevice {

    private DeviceRepository deviceRepository;

    public UpdateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(String rawImei, UpdateRequestDevice updateRequestDevice) throws DeviceNotFound, InvalidImei {
        Imei imei = Imei.create(rawImei);
        Device device = deviceRepository.getBy(imei);

        Device updatedDevice = device.update(updateRequestDevice.getOperatingSystemVersion());

        deviceRepository.update(updatedDevice);
    }
}
