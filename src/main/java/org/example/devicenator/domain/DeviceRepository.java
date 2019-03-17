package org.example.devicenator.domain;

public interface DeviceRepository {

    void save(Device device);

    Device getBy(String imei);
}
