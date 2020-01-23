package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.authenticateuser.AuthenticationRequest;
import org.example.devicenator.application.authenticateuser.AuthenticationResponse;
import org.example.devicenator.application.authenticateuser.GetUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final GetUserToken getUserToken;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, GetUserToken getUserToken) {
        this.authenticationManager = authenticationManager;
        this.getUserToken = getUserToken;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> execute(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getRawEmail(), authenticationRequest.getPassword());

        String token = getUserToken.execute(authenticationRequest.getRawEmail());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
