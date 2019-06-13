package org.example.devicenator.application.updatedevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;


public class UpdateDevice {

    private DeviceRepository deviceRepository;

    public UpdateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(String imei, UpdateRequestDevice updateRequestDevice) throws DeviceNotFound {
        Device device = deviceRepository.getBy(imei);

        Device updatedDevice = device.update(updateRequestDevice.getOperatingSystemVersion());

        deviceRepository.update(updatedDevice);
    }
}
