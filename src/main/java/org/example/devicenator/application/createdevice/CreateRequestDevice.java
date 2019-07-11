package org.example.devicenator.application.createdevice;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateRequestDevice {

    @NotNull @Imei
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
