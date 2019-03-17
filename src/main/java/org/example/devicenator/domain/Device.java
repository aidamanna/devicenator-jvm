package org.example.devicenator.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.devicenator.application.createdevice.CreateRequestDevice;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Device {

    private final String imei;
    private final String vendor;
    private final String model;
    private final String operatingSystem;
    private final String operatingSystemVersion;

    public static Device create(CreateRequestDevice createRequestDevice) {
        return new Device(
                createRequestDevice.getImei(),
                createRequestDevice.getVendor(),
                createRequestDevice.getModel(),
                createRequestDevice.getOperatingSystem(),
                createRequestDevice.getOperatingSystemVersion()
        );
    }
}
