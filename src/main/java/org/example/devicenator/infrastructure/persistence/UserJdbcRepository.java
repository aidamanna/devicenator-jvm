package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.user.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) throws UserAlreadyExists {
        String saveUserQuery = "INSERT INTO users VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                    saveUserQuery,
                    user.getEmail(),
                    user.getName(),
                    user.getSurname(),
                    user.getEncodedPassword());
        } catch (DuplicateKeyException exception) {
            throw new UserAlreadyExists();
        }
    }

    @Override
    public User getBy(Email email) throws UserNotFound {
        String getUserByEmailQuery = "SELECT * FROM users WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(
                    getUserByEmailQuery,
                    new Object[]{email.getEmail()},
                    new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound();
        }
    }

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return new User(
                        Email.create(rs.getString("email")),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"));
            } catch (InvalidEmail e) {
                throw new SQLException();
            }
        }
    }
}
