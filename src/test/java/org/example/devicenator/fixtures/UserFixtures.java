package org.example.devicenator.fixtures;

import org.example.devicenator.application.registeruser.RegisterRequestUser;
import org.example.devicenator.domain.user.Email;
import org.example.devicenator.domain.user.User;

public class UserFixtures {

    private static final String NAME = "Jane";
    private static final String SURNAME = "Brown";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    public static User aUser(String email) {
        return new User(new Email(email), ENCODED_PASSWORD, NAME, SURNAME);
    }

    public static RegisterRequestUser aRegisterRequestUser(String email) {
        return new RegisterRequestUser(email, PASSWORD, NAME, SURNAME);
    }

    public static String aRegisterUserJson(String email) {
        return String.format("{\"username\":\"%s\"," +
                        "\"password\":\"%s\"," +
                        "\"name\":\"%s\"," +
                        "\"surname\":\"%s\"}",
                email, PASSWORD, NAME, SURNAME);
    }

    public static String aExistingUserResponseJson() {
        return aResponseJson("EXISTING_USER", "The user is registered");
    }

    public static String anInvalidEmailResponseJson() {
        return aResponseJson("INVALID_EMAIL", "The user email is invalid");
    }

    private static String aResponseJson(String error, String reason) {
        return String.format("{\"error\": \"%s\", \"reason\": \"%s\"}", error, reason);
    }
}
