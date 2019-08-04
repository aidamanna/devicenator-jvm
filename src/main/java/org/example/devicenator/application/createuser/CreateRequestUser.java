package org.example.devicenator.application.createuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class CreateRequestUser {

    @JsonProperty("email")
    @NotBlank
    private String rawEmail;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;
}
