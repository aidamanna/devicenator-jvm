package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class CreateDeviceController {

    private CreateDevice createDevice;

    @Autowired
    public CreateDeviceController(CreateDevice createDevice) {
        this.createDevice = createDevice;
    }

    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@Valid @RequestBody CreateRequestDevice device) throws DeviceAlreadyExists {
        createDevice.execute(device);
    }
}

