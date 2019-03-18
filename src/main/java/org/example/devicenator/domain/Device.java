package org.example.devicenator.domain;

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

}
