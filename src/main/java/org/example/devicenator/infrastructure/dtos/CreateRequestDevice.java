package org.example.devicenator.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateRequestDevice {

    @NotBlank
    private String vendor;

    @NotBlank
    private String model;

    @NotBlank
    private String operatingSystem;

    @NotNull
    private Integer operatingSystemVersion;
}
