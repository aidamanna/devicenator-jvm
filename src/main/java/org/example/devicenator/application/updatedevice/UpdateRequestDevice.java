package org.example.devicenator.application.updatedevice;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UpdateRequestDevice {

    @NotBlank
    private final String operatingSystemVersion;
}
