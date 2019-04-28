package org.example.devicenator.domain.device;

public interface DeviceRepository {

    void save(Device device) throws DeviceAlreadyExists;

    Device getBy(String imei) throws DeviceNotFound;

    void update(Device device);

    void delete(String imei);
}
