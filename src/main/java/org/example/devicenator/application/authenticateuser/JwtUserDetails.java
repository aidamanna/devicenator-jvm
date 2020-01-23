package org.example.devicenator.application.authenticateuser;


import org.example.devicenator.domain.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class JwtUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String rawEmail) throws UsernameNotFoundException {
        User user;

        try {
            user = userRepository.getBy(Email.create(rawEmail));
        } catch (UserException userNotFound) {
            throw new UsernameNotFoundException("The user with the email: " + rawEmail + "is not registered");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncodedPassword(),
                new ArrayList<>());
    }
}
