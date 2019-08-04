package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.user.User;
import org.example.devicenator.domain.user.UserAlreadyExists;
import org.example.devicenator.domain.user.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) throws UserAlreadyExists {
        String saveUserQuery = "INSERT INTO users VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(
                    saveUserQuery,
                    user.getEmail(),
                    user.getName(),
                    user.getSurname());
        } catch (DuplicateKeyException exception) {
            throw new UserAlreadyExists();
        }
    }
}
