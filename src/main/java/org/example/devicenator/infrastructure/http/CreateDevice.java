package org.example.devicenator.infrastructure.http;

import org.example.devicenator.infrastructure.dtos.CreateRequestDevice;
import org.example.devicenator.infrastructure.persistence.DeviceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/devices")
public class CreateDevice {

    private DeviceCreator deviceCreator;

    @Autowired
    public CreateDevice(DeviceCreator deviceCreator) {
        this.deviceCreator = deviceCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(
            @Valid @RequestBody final CreateRequestDevice device) {
        deviceCreator.execute(device);
    }
}

