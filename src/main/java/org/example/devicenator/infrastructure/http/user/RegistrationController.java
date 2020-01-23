package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.registeruser.RegisterRequestUser;
import org.example.devicenator.application.registeruser.RegisterUser;
import org.example.devicenator.domain.user.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private RegisterUser registerUser;

    @Autowired
    public RegistrationController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@Valid @RequestBody RegisterRequestUser user) throws UserException {
        registerUser.execute(user);
    }
}
