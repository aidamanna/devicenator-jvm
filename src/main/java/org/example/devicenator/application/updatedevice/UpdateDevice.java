package org.example.devicenator.application.updatedevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.DeviceRepository;


public class UpdateDevice {

    private DeviceRepository deviceRepository;

    public UpdateDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void execute(UpdateRequestDevice updateRequestDevice) throws DeviceNotFound {
        Device device = deviceRepository.getBy(updateRequestDevice.getImei());

        Device updatedDevice = device.update(
                updateRequestDevice.getVendor(),
                updateRequestDevice.getModel(),
                updateRequestDevice.getOperatingSystem(),
                updateRequestDevice.getOperatingSystemVersion());

        deviceRepository.update(updatedDevice);
    }
}
