package org.example.devicenator.domain.device;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    public Device update(String vendor, String model, String operatingSystem, String operatingSystemVersion) {
        return new Device(imei, vendor, model, operatingSystem, operatingSystemVersion);
    }
}
