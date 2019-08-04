package org.example.devicenator.application.createuser;

import org.example.devicenator.domain.user.UserException;
import org.example.devicenator.infrastructure.persistence.UserJdbcRepository;
import org.junit.Before;
import org.junit.Test;

import static org.example.devicenator.fixtures.UserFixtures.aCreateRequestUser;
import static org.example.devicenator.fixtures.UserFixtures.aUser;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateUserTest {

    private static final String RAW_EMAIL = "jane@example.com";

    private CreateUser createUser;
    private UserJdbcRepository userRepository;

    @Before
    public void setUp() {
        userRepository = mock(UserJdbcRepository.class);
        createUser = new CreateUser(userRepository);
    }

    @Test
    public void createsAUser() throws UserException {
        createUser.execute(aCreateRequestUser(RAW_EMAIL));

        verify(userRepository).save(aUser(RAW_EMAIL));
    }
}