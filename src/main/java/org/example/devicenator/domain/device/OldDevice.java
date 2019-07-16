package org.example.devicenator.domain.device;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class OldDevice {

    private final String imei;
    private final String vendor;
    private final String model;
    private final String operatingSystem;
    private final String operatingSystemVersion;

    public OldDevice update(String operatingSystemVersion) {
        return new OldDevice(imei, vendor, model, operatingSystem, operatingSystemVersion);
    }
}
