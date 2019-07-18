package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.InvalidImei;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CreateDeviceController {

    private CreateDevice createDevice;
    private Logger logger;

    @Autowired
    public CreateDeviceController(CreateDevice createDevice, Logger logger) {
        this.createDevice = createDevice;
        this.logger = logger;
    }

    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@Valid @RequestBody CreateRequestDevice device)
        throws DeviceAlreadyExists, InvalidImei {
        createDevice.execute(device);

        logger.info("Device created");
    }
}

