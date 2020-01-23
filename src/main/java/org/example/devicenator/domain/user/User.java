package org.example.devicenator.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class User {

    private final Email email;
    private final String password;
    private final String name;
    private final String surname;

    public String getEmail() {
        return this.email.getEmail();
    }
}
