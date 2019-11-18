package org.example.devicenator.application.listdevices;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceRepository;

import java.util.List;

public class ListDevices {
    private DeviceRepository deviceRepository;

    public ListDevices(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> execute() {
        return deviceRepository.getList();
    }
}
