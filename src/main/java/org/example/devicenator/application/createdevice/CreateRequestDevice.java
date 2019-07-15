package org.example.devicenator.application.createdevice;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateRequestDevice {

    @NotBlank
    private String imei;

    @NotBlank
    private String vendor;

    @NotBlank
    private String model;

    @NotBlank
    private String operatingSystem;

    @NotBlank
    private String operatingSystemVersion;
}
