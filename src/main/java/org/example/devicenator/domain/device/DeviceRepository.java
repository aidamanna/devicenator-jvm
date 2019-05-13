package org.example.devicenator.domain.device;

import io.vavr.control.Try;

import javax.xml.ws.Response;

public interface DeviceRepository {

    Try<Response> save(Device device);

    Device getBy(String imei) throws DeviceNotFound;

    void update(Device device);

    void delete(String imei);
}
