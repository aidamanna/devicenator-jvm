package org.example.devicenator.infrastructure.http.user;

import org.example.devicenator.application.createuser.CreateRequestUser;
import org.example.devicenator.application.createuser.CreateUser;
import org.example.devicenator.domain.user.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CreateUserController {

    private CreateUser createUser;

    @Autowired
    public CreateUserController(CreateUser createUser) {
        this.createUser = createUser;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@Valid @RequestBody CreateRequestUser user) throws UserException {
        createUser.execute(user);
    }
}
