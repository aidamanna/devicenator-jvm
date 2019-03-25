package org.example.devicenator.domain;

import org.example.devicenator.domain.device.DeviceNotFound;

public interface DeviceRepository {

    void save(Device device);

    Device getBy(String imei) throws DeviceNotFound;
}
