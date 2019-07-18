package org.example.devicenator.domain.user;

public class InvalidEmail extends UserException {

    public InvalidEmail() {
        super("The user email is invalid");
    }
}
