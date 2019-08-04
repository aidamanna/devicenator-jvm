package org.example.devicenator.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    private final Email email;
    private final String name;
    private final String surname;
}
