package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.registeruser.RegisterUser;
import org.example.devicenator.domain.user.InvalidEmail;
import org.example.devicenator.domain.user.UserAlreadyExists;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.fixtures.UserFixtures.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest {

    private static final String RAW_EMAIL = "jane@example.com";
    private static final String EXISTING_RAW_EMAIL = "jane@example.com";
    private static final String INVALID_RAW_EMAIL = "jane@example";
    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private RegisterUser registerUser;

    @Before
    public void setUp() {
        registerUser = mock(RegisterUser.class);
        RegistrationController registrationController = new RegistrationController(registerUser);

        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void registersAUser() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aRegisterUserJson(RAW_EMAIL)))
                .andExpect(status().isCreated());

        verify(registerUser).execute(aRegisterRequestUser(RAW_EMAIL));
    }

    @Test
    public void returnsConflictWhenTheUserIsRegistered() throws Exception {
        doThrow(new UserAlreadyExists()).when(registerUser).execute(aRegisterRequestUser(EXISTING_RAW_EMAIL));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aRegisterUserJson(EXISTING_RAW_EMAIL)))
                .andExpect(status().isConflict())
                .andExpect(content().json(aExistingUserResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenEmailIsInvalid() throws Exception {
        doThrow(new InvalidEmail()).when(registerUser).execute(aRegisterRequestUser(INVALID_RAW_EMAIL));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aRegisterUserJson(INVALID_RAW_EMAIL)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(anInvalidEmailResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenNotSpecifyingUserValues() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(registerUser, never()).execute(any());
    }
}