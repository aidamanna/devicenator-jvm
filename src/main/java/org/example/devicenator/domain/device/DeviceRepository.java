package org.example.devicenator.domain.device;

public interface DeviceRepository {

    void save(Device device) throws DeviceAlreadyExists;

    Device getBy(Imei imei) throws DeviceNotFound;

    void update(Device device);

    void delete(Imei imei);
}
