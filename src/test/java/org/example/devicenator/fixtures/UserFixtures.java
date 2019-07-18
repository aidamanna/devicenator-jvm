package org.example.devicenator.fixtures;

import org.example.devicenator.application.createuser.CreateRequestUser;

public class UserFixtures {

    private static final String NAME = "Jane";
    private static final String SURNAME = "Brown";

    public static CreateRequestUser aCreateRequestUser(String email) {
        return new CreateRequestUser(email, NAME, SURNAME);
    }

    public static String aCreateUserJson(String email) {
        return String.format("{\"email\":\"%s\"," +
                        "\"name\":\"%s\"," +
                        "\"surname\":\"%s\"}",
                email, NAME, SURNAME);
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
