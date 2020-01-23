package org.example.devicenator.domain.user;

public class UserNotFound extends UserException {

    public UserNotFound() {
        super("The user is not registered");
    }
}
