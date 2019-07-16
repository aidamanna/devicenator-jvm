package org.example.devicenator.domain.device;

public interface DeviceRepository {

    void save(OldDevice device) throws DeviceAlreadyExists;

    OldDevice getBy(String imei) throws DeviceNotFound;

    void update(OldDevice device);

    void delete(String imei);
}
