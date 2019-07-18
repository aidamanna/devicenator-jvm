package org.example.devicenator.application.createuser;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public class CreateRequestUser {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;
}
