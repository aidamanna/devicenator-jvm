package org.example.devicenator.infrastructure.http.user;

import javax.validation.Valid;
import org.example.devicenator.application.authenticateuser.AuthenticationRequest;
import org.example.devicenator.application.authenticateuser.AuthenticationResponse;
import org.example.devicenator.application.authenticateuser.GetJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final GetJwtUser getJwtUser;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
        GetJwtUser getJwtUser) {
        this.authenticationManager = authenticationManager;
        this.getJwtUser = getJwtUser;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    AuthenticationResponse execute(@Valid @RequestBody AuthenticationRequest authenticationRequest)
        throws Exception {
        authenticate(authenticationRequest.getRawEmail(), authenticationRequest.getPassword());

        String token = getJwtUser.token(authenticationRequest.getRawEmail());

        return new AuthenticationResponse(token);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
