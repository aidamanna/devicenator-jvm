package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.authenticateuser.JwtUserDetails;
import org.example.devicenator.application.authenticateuser.JwtTokenRequest;
import org.example.devicenator.application.authenticateuser.JwtTokenResponse;
import org.example.devicenator.infrastructure.configuration.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final JwtUserDetails userDetailsService;

    @Autowired
    public JwtAuthenticationController(
        AuthenticationManager authenticationManager, JwtToken jwtToken,
        JwtUserDetails userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtToken.generate(userDetails);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
