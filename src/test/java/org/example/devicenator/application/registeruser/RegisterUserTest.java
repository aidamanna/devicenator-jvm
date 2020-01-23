package org.example.devicenator.application.registeruser;

import org.example.devicenator.domain.user.UserException;
import org.example.devicenator.infrastructure.persistence.UserJdbcRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.example.devicenator.fixtures.UserFixtures.aRegisterRequestUser;
import static org.example.devicenator.fixtures.UserFixtures.aUser;
import static org.mockito.Mockito.*;

public class RegisterUserTest {

    private static final String RAW_EMAIL = "jane@example.com";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    private RegisterUser registerUser;
    private UserJdbcRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userRepository = mock(UserJdbcRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registerUser = new RegisterUser(userRepository, passwordEncoder);
    }

    @Test
    public void createsAUser() throws UserException {
        RegisterRequestUser registerRequestUser = aRegisterRequestUser(RAW_EMAIL);
        when(passwordEncoder.encode(registerRequestUser.getPassword())).thenReturn(ENCODED_PASSWORD);

        registerUser.execute(registerRequestUser);

        verify(userRepository).save(aUser(RAW_EMAIL));
    }
}