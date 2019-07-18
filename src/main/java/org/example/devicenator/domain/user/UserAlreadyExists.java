package org.example.devicenator.domain.user;

public class UserAlreadyExists extends UserException {

    public UserAlreadyExists() {
        super("The user is registered");
    }
}
