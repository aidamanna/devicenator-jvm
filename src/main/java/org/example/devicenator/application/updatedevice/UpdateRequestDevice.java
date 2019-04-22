package org.example.devicenator.application.updatedevice;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UpdateRequestDevice {

    @NotBlank
    @Pattern(regexp="\\d{15}")
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
