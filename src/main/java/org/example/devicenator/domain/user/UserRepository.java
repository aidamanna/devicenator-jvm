package org.example.devicenator.domain.user;

public interface UserRepository {

    void save(User user) throws UserAlreadyExists;
}
