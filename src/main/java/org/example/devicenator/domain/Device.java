package org.example.devicenator.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.devicenator.application.createdevice.CreateRequestDevice;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Device {

    private final String imei;
    private final String vendor;
    private final String model;
    private final String operatingSystem;
    private final int operatingSystemVersion;

    public static Device create(CreateRequestDevice createRequestDevice) {
        return new Device(
                createRequestDevice.imei,
                createRequestDevice.vendor,
                createRequestDevice.model,
                createRequestDevice.operatingSystem,
                createRequestDevice.operatingSystemVersion
        );
    }
}
