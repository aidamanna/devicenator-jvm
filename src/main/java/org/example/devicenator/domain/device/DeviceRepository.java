package org.example.devicenator.domain.device;

public interface DeviceRepository {

    void save(Device device);

    Device getBy(String imei) throws DeviceNotFound;
}
