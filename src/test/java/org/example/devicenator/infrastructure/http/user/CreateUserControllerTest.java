package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.createuser.CreateUser;
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

public class CreateUserControllerTest {

    private static final String RAW_EMAIL = "jane@example.com";
    private static final String EXISTING_RAW_EMAIL = "jane@example.com";
    private static final String INVALID_RAW_EMAIL = "jane@example";
    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private CreateUser createUser;

    @Before
    public void setUp() {
        createUser = mock(CreateUser.class);
        CreateUserController createUserController = new CreateUserController(createUser);

        mockMvc = MockMvcBuilders.standaloneSetup(createUserController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void createsAUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateUserJson(RAW_EMAIL)))
                .andExpect(status().isCreated());

        verify(createUser).execute(aCreateRequestUser(RAW_EMAIL));
    }

    @Test
    public void returnsConflictWhenTheUserAlreadyExists() throws Exception {
        doThrow(new UserAlreadyExists()).when(createUser).execute(aCreateRequestUser(EXISTING_RAW_EMAIL));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateUserJson(EXISTING_RAW_EMAIL)))
                .andExpect(status().isConflict())
                .andExpect(content().json(aExistingUserResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenEmailIsInvalid() throws Exception {
        doThrow(new InvalidEmail()).when(createUser).execute(aCreateRequestUser(INVALID_RAW_EMAIL));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateUserJson(INVALID_RAW_EMAIL)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(anInvalidEmailResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenNotSpecifyingUserValues() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(createUser, never()).execute(any());
    }
}