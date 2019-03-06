package org.example.devicenator.application.createdevice;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateRequestDevice {

    @NotBlank
    private String imei;

    @NotBlank
    private String vendor;

    @NotBlank
    private String model;

    @NotBlank
    private String operatingSystem;

    @NotNull
    private Integer operatingSystemVersion;
}
