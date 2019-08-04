package org.example.devicenator.application.createuser;

import org.example.devicenator.domain.user.Email;
import org.example.devicenator.domain.user.User;
import org.example.devicenator.domain.user.UserException;
import org.example.devicenator.domain.user.UserRepository;

public class CreateUser {

    private final UserRepository userRepository;

    public CreateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(CreateRequestUser createRequestUser) throws UserException {
        User user = new User(
                Email.create(createRequestUser.getRawEmail()),
                createRequestUser.getName(),
                createRequestUser.getSurname());

        userRepository.save(user);
    }
}

