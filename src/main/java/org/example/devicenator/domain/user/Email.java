package org.example.devicenator.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.validator.routines.EmailValidator;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Email {

    private final String email;

    public static Email create(String rawEmail) throws InvalidEmail {
        if (EmailValidator.getInstance().isValid(rawEmail)) {
            return new Email(rawEmail);
        }

        throw new InvalidEmail();
    }
}
