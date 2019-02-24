package org.example.devicenator.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateRequestDevice {

    private String vendor;
    private String model;
    private String operatingSystem;
    private String operatingSystemVersion;
}
