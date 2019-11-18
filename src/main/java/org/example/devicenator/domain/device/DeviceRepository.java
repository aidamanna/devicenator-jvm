package org.example.devicenator.domain.device;

import java.util.List;

public interface DeviceRepository {

    void save(Device device) throws DeviceAlreadyExists;

    Device getBy(Imei imei) throws DeviceNotFound;

    List<Device> getList();

    void update(Device device);

    void delete(Imei imei);
}
