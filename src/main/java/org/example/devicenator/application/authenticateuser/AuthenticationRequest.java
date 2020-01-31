package org.example.devicenator.application.authenticateuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @JsonProperty("username")
    @NotBlank
    private String rawEmail;

    @NotBlank
    private String password;
}