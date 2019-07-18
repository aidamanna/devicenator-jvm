package org.example.devicenator.application.updatedevice;

import org.example.devicenator.domain.device.*;


public class UpdateDevice {

    private DeviceRepository deviceRepository;

    public UpdateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(String imei, UpdateRequestDevice updateRequestDevice) throws DeviceNotFound, InvalidImei {
        Device device = deviceRepository.getBy(Imei.create(imei));

        Device updatedDevice = device.update(updateRequestDevice.getOperatingSystemVersion());

        deviceRepository.update(updatedDevice);
    }
}
