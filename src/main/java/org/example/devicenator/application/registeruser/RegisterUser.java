package org.example.devicenator.application.registeruser;

import org.example.devicenator.domain.user.Email;
import org.example.devicenator.domain.user.User;
import org.example.devicenator.domain.user.UserException;
import org.example.devicenator.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(RegisterRequestUser registerRequestUser) throws UserException {
        User user = new User(
                Email.create(registerRequestUser.getRawEmail()),
                passwordEncoder.encode(registerRequestUser.getPassword()),
                registerRequestUser.getName(),
                registerRequestUser.getSurname());

        userRepository.save(user);
    }
}

