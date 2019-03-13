package org.example.devicenator.application.createdevice;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRequestDevice {

    @NotBlank
    public final String imei;

    @NotBlank
    public final String vendor;

    @NotBlank
    public final String model;

    @NotBlank
    public final String operatingSystem;

    @NotNull
    public final Integer operatingSystemVersion;
}
