package org.example.devicenator.application.createdevice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateRequestDevice {

    @JsonProperty("imei")
    @NotBlank
    private String rawImei;

    @NotBlank
    private String vendor;

    @NotBlank
    private String model;

    @NotBlank
    private String operatingSystem;

    @NotBlank
    private String operatingSystemVersion;
}
