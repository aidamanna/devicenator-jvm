package org.example.devicenator.infrastructure.http.user;

import static org.example.devicenator.fixtures.UserFixtures.aAuthenticationRequestJson;
import static org.example.devicenator.fixtures.UserFixtures.aAuthenticationResponseJson;
import static org.example.devicenator.fixtures.UserFixtures.aNonRegisteredUserResponseJson;
import static org.example.devicenator.fixtures.UserFixtures.anInvalidEmailResponseJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.devicenator.application.authenticateuser.GetJwtUser;
import org.example.devicenator.domain.user.InvalidEmail;
import org.example.devicenator.domain.user.UserNotFound;
import org.example.devicenator.infrastructure.configuration.JwtAuthenticationEntryPoint;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
public class AuthenticationControllerTest {

    public static final String TOKEN = "xxxxx.yyyyy.zzzzz";
    private static final String RAW_EMAIL = "jane@example.com";
    private static final String UNREGISTERED_RAW_EMAIL = "john@example.com";
    private static final String INVALID_RAW_EMAIL = "jane@example";
    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private AuthenticationManager authenticationManager;
    private GetJwtUser getJwtUser;

    @Before
    public void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        getJwtUser = mock(GetJwtUser.class);
        AuthenticationController authenticationController =
            new AuthenticationController(authenticationManager, getJwtUser);

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
            .setControllerAdvice(GlobalExceptionHandler.class)
            .build();
    }

    @Test
    public void authenticatesAUser() throws Exception {
        when(getJwtUser.token(RAW_EMAIL)).thenReturn(TOKEN);

        mockMvc.perform(post("/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(aAuthenticationRequestJson(RAW_EMAIL)))
            .andExpect(status().isOk())
            .andExpect(content().json(aAuthenticationResponseJson(TOKEN)));
    }
}