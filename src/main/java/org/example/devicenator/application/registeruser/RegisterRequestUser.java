package org.example.devicenator.application.registeruser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RegisterRequestUser {

    @JsonProperty("email")
    @NotBlank
    private String rawEmail;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;
}
