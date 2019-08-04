package org.example.devicenator.domain.user;

import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void createsEmailIfValidRawEmail() throws InvalidEmail {
        Email email = Email.create("jane@example.com");

        assertThat(email, instanceOf(Email.class));
    }

    @Test(expected = InvalidEmail.class)
    public void throwsInvalidEmailIfInvalidRawEmail() throws InvalidEmail {
        Email.create("jane@example");
    }
}
