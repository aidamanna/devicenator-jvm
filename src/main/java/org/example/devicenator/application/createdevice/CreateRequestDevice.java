package org.example.devicenator.application.createdevice;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateRequestDevice {

    @NotBlank
    private final String imei;

    @NotBlank
    private final String vendor;

    @NotBlank
    private final String model;

    @NotBlank
    private final String operatingSystem;

    @NotBlank
    private final String operatingSystemVersion;
}
