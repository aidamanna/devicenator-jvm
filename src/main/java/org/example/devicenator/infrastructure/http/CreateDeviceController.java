package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/devices")
public class CreateDeviceController {

    private CreateDevice createDevice;

    @Autowired
    public CreateDeviceController(CreateDevice createDevice) {
        this.createDevice = createDevice;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(
            @Valid @RequestBody final CreateRequestDevice device) {
        createDevice.execute(device);
    }
}

