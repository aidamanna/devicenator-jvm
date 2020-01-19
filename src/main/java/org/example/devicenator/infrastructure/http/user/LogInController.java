package org.example.devicenator.infrastructure.http.user;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController {

    private Logger logger;

    public LogInController(Logger logger) {
        this.logger = logger;
    }

    @GetMapping("/login")
    public void authenticate() {
        logger.info("User logged in");
    }
}
