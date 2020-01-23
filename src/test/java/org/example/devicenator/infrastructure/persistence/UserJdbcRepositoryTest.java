package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.user.*;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.example.devicenator.fixtures.UserFixtures.aUser;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserJdbcRepositoryTest {

    private static final String RAW_EMAIL = "jane@example.com";
    private static final String UNKNOWN_RAW_EMAIL = "john@example.com";
    private UserJdbcRepository userRepository;
    private Flyway flyway;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        DataSource dataSource = dataSourceBuilder.build();

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        jdbcTemplate = new JdbcTemplate(dataSource);

        userRepository = new UserJdbcRepository(jdbcTemplate);
    }

    @After
    public void tearDown() {
        flyway.clean();
    }

    @Test
    public void savesAUser() throws UserAlreadyExists {
        userRepository.save(aUser(RAW_EMAIL));

        Integer userCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                new Object[]{RAW_EMAIL},
                Integer.class);

        assertThat(userCount, is(1));
    }

    @Test(expected = UserAlreadyExists.class)
    public void throwsUserAlreadyExistsExceptionWhenSavingAnExistingDevice() throws UserAlreadyExists {
        User user = aUser(RAW_EMAIL);
        userRepository.save(user);

        userRepository.save(user);
    }

    @Test
    public void retrievesAUser() throws UserException {
        User user = aUser(RAW_EMAIL);
        userRepository.save(user);

        User savedUser = userRepository.getBy(Email.create(RAW_EMAIL));

        assertThat(savedUser, is(user));
    }

    @Test(expected = UserNotFound.class)
    public void throwsExceptionWhenRetrievingANonExistingUser() throws UserException {
        userRepository.getBy(Email.create(UNKNOWN_RAW_EMAIL));
    }
}
