package org.example.devicenator.domain.user;

public interface UserRepository {

    void save(User user) throws UserAlreadyExists;

    User getBy(Email email) throws UserNotFound;
}
