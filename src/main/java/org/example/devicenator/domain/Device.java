package org.example.devicenator.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.devicenator.application.createdevice.CreateRequestDevice;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Device {

    private String imei;
    private String vendor;
    private String model;
    private String operatingSystem;
    private int operatingSystemVersion;

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
